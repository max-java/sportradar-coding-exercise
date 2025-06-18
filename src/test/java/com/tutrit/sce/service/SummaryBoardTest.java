package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import com.tutrit.sce.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SummaryBoardTest {

    @MockitoBean
    private GameRepository gameRepository;
    @Autowired
    private SummaryBoard summaryBoard;
    @MockitoBean(name = "summaryBoardConsoleDisplay")
    private BoardDisplay summaryBoardConsoleDisplay;
    @Captor
    private ArgumentCaptor<List<Game>> gameListCaptor;

    @Test
    void displayBoard() {
        Game mexicoCanada = game("Mexico", "Canada", 0, 5, "2025-06-18T10:00:00Z");
        Game spainBrazil = game("Spain", "Brazil", 10, 2, "2025-06-18T10:01:00Z");
        Game germanyFrance = game("Germany", "France", 2, 2, "2025-06-18T10:02:00Z");
        Game uruguayItaly = game("Uruguay", "Italy", 6, 6, "2025-06-18T10:03:00Z");
        Game argentinaAustralia = game("Argentina", "Australia", 3, 1, "2025-06-18T10:04:00Z");

        when(gameRepository.findAll()).thenReturn(List.of(mexicoCanada, spainBrazil, germanyFrance, uruguayItaly, argentinaAustralia));

        summaryBoard.displayBoard();

        verify(summaryBoardConsoleDisplay).showBoard(gameListCaptor.capture());

        List<Game> displayed = gameListCaptor.getValue();

        assertEquals(List.of(uruguayItaly, spainBrazil, mexicoCanada, argentinaAustralia, germanyFrance), displayed);
    }

    private Game game(String homeTeam, String awayTeam, int homeScore, int awayScore, String dateAdded) {
        return Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeScore(homeScore)
                .awayScore(awayScore)
                .dateAdded(java.time.Instant.parse(dateAdded))
                .startTime(Instant.now())
                .endTime(Instant.now())
                .build();
    }
}
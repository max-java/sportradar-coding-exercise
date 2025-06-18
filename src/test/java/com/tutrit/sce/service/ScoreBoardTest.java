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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScoreBoardTest {
    @MockitoBean
    private GameRepository gameRepository;
    @MockitoBean(name = "scoreBoardConsoleDisplay")
    private BoardDisplay scoreBoardConsoleDisplay;
    @Autowired
    private ScoreBoard scoreBoard;
    @Captor
    private ArgumentCaptor<List<Game>> gameListCaptor;

    @Test
    void startGame_shouldInitializeGame() {
        Game game = game("Spain", "Brazil", null, null, "2025-06-18T10:00:00Z");
        when(gameRepository.findById(game.getId())).thenReturn(game);

        scoreBoard.startGame(game.getId());

        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertNotNull(game.getStartTime());

        verify(gameRepository).save(game);
    }

    @Test
    void updateScore_shouldModifyAndSaveGame() {
        Game game = game("Germany", "France", 0, 0, "2025-06-18T10:00:00Z");
        when(gameRepository.findById(game.getId())).thenReturn(game);

        scoreBoard.updateScore(game.getId(), 3, 2);

        assertEquals(3, game.getHomeScore());
        assertEquals(2, game.getAwayScore());

        verify(gameRepository).save(game);
    }

    @Test
    void finishGame_shouldSetEndTimeAndRemoveFromCache() {
        Game game = game("Argentina", "Australia", 1, 1, "2025-06-18T10:05:00Z");
        when(gameRepository.findById(game.getId())).thenReturn(game);
        scoreBoard.startGame(game.getId());
        when(gameRepository.findById(game.getId())).thenReturn(game);

        scoreBoard.finishGame(game.getId());

        assertNotNull(game.getEndTime());
        verify(gameRepository, times(2)).save(game); // once on start, once on finish
    }


    @Test
    void displayBoard() {
        Game mexicoCanada = game("Mexico", "Canada", 0, 5, "2025-06-18T10:00:00Z");
        Game spainBrazil = game("Spain", "Brazil", 10, 2, "2025-06-18T10:01:00Z");

        when(gameRepository.findById(mexicoCanada.getId())).thenReturn(mexicoCanada);
        when(gameRepository.findById(spainBrazil.getId())).thenReturn(spainBrazil);

        scoreBoard.startGame(mexicoCanada.getId());
        scoreBoard.startGame(spainBrazil.getId());

        when(gameRepository.findById(mexicoCanada.getId())).thenReturn(mexicoCanada);
        when(gameRepository.findById(spainBrazil.getId())).thenReturn(spainBrazil);

        scoreBoard.displayBoard();

        verify(scoreBoardConsoleDisplay).showBoard(gameListCaptor.capture());
        List<Game> displayed = gameListCaptor.getValue();

        assertEquals(List.of(mexicoCanada, spainBrazil), displayed);
    }

    private Game game(String homeTeam, String awayTeam, Integer homeScore, Integer awayScore, String dateAdded) {
        return Game.builder()
                .id(UUID.randomUUID())
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeScore(homeScore)
                .awayScore(awayScore)
                .dateAdded(Instant.parse(dateAdded))
                .startTime(Instant.now())
                .build();
    }
}
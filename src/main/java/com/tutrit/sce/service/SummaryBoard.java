package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import com.tutrit.sce.repository.GameRepository;
import com.tutrit.sce.util.GameComparators;
import com.tutrit.sce.util.GameFilters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryBoard {
    private final GameRepository gameRepository;
    private final BoardDisplay display;

    public SummaryBoard(GameRepository gameRepository,
                        BoardDisplay summaryBoardConsoleDisplay) {
        this.gameRepository = gameRepository;
        this.display = summaryBoardConsoleDisplay;
    }

    private List<Game> getLatestDataBoard() {
         return gameRepository.findAll().stream()
                .filter(GameFilters::gameStartedAndFinished)
                .sorted(GameComparators.BY_TOTAL_SCORE_DESC.thenComparing(GameComparators.BY_DATE_ADDED_DESC))
                .toList();
    }

    @Scheduled(fixedRate = 1000)
    public void displayBoard() {
        display.showBoard(getLatestDataBoard());
    }
}

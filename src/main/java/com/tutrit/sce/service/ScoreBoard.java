package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import com.tutrit.sce.repository.GameRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ScoreBoard {
    private final List<UUID> currentGamesIdsCache = new ArrayList<>();
    private final GameRepository gameRepository;
    private final BoardDisplay display;

    public ScoreBoard(GameRepository gameRepository,
                      BoardDisplay scoreBoardConsoleDisplay) {
        this.gameRepository = gameRepository;
        this.display = scoreBoardConsoleDisplay;
    }

    public void startGame(UUID gameId) {
        Game game = gameRepository.findById(gameId);
        game.setAwayScore(0);
        game.setHomeScore(0);
        game.setStartTime(java.time.Instant.now());
        currentGamesIdsCache.add(game.getId());
        gameRepository.save(game);
    }

    public void updateScore(UUID gameId, int homeScore, int awayScore) {
        Game game = gameRepository.findById(gameId);
        game.setAwayScore(awayScore);
        game.setHomeScore(homeScore);
        gameRepository.save(game);
    }

    public void finishGame(UUID gameId) {
        Game game = gameRepository.findById(gameId);
        game.setEndTime(java.time.Instant.now());
        gameRepository.save(game);

        currentGamesIdsCache.stream()
                .filter(id -> id.equals(game.getId()))
                .findFirst()
                .ifPresent(currentGamesIdsCache::remove);

    }

    @Scheduled(fixedRate = 1000)
    public void displayBoard() {
        display.showBoard(getLastUpdates());
    }

    private List<Game> getLastUpdates() {
        return currentGamesIdsCache.stream()
                .map(gameRepository::findById)
                .filter(Objects::nonNull)
                .toList();
    }
}

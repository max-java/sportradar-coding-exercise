package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import com.tutrit.sce.repository.CountryRepository;
import com.tutrit.sce.repository.GameRepository;

import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class GameEngine implements Runnable{
    private final CountryRepository countryRepository;
    private final GameRepository gameRepository;
    private final ScoreBoard scoreBoard;

    public GameEngine(CountryRepository countryRepository,
                      GameRepository gameRepository,
                      ScoreBoard scoreBoard) {
        this.countryRepository = countryRepository;
        this.gameRepository = gameRepository;
        this.scoreBoard = scoreBoard;
    }

    /**
     * Manages a game with random home and away teams.
     * The game is assigned a unique ID and the current timestamp as the date added to out system.
     * The created game is then added to the game repository.
     * Game is started shortly after creation.
     * Game lasts for 30 seconds, after which it is stopped.
     * During the game, score been updated randomly every 1 second.
     */
    @Override
    public void run() {
        var scheduler = newScheduledThreadPool(1);
        var game = startGame();
        var scoreUpdater = updateScoreRandomlyEverySecond(scheduler, game);
        finishGameAfter10Seconds(scheduler, scoreUpdater, game);
    }

    private Game startGame() {
        Game game = gameRepository.save(createGame());
        scoreBoard.startGame(game.getId());
        return game;
    }

    private void finishGameAfter10Seconds(ScheduledExecutorService scheduler, ScheduledFuture<?> scoreUpdater, Game game) {
        scheduler.schedule(() -> {
            scoreUpdater.cancel(false);
            scoreBoard.finishGame(game.getId());
            scheduler.shutdown();
        }, 10, TimeUnit.SECONDS);
    }

    private ScheduledFuture<?> updateScoreRandomlyEverySecond(ScheduledExecutorService scheduler, Game game) {
        return scheduler.scheduleAtFixedRate(() -> {
            int homeIncrement = Math.random() < 0.8 ? 1 : 0;
            int awayIncrement = Math.random() < 0.8 ? 1 : 0;

            var current = gameRepository.findById(game.getId());
            int newHome = current.getHomeScore() + homeIncrement;
            int newAway = current.getAwayScore() + awayIncrement;
            scoreBoard.updateScore(game.getId(), newHome, newAway);
        }, 0, 1, TimeUnit.SECONDS);
    }

    private Game createGame() {
        var game = Game.builder()
                .homeTeam(countryRepository.getRandomCountry())
                .awayTeam(countryRepository.getRandomCountry())
                .dateAdded(Instant.now())
                .build();
        return gameRepository.save(game);
    }
}

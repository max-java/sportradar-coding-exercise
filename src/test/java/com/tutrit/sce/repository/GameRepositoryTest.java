package com.tutrit.sce.repository;

import com.tutrit.sce.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GameRepositoryTest {
    @Autowired
    GameRepository gameRepository;

    @Test
    void save() {
        Game game = makeGame();
        Game savedGame = gameRepository.save(game);
        assertNotNull(savedGame, "Saved game should not be null");
        assertNotNull(savedGame.getId(), "ID should be auto-generated");
        assertEquals(savedGame, gameRepository.findById(savedGame.getId()));
    }

    @Test
    void updateGame() {
        Game game = makeGame();
        game = gameRepository.save(game);

        var initialSize = gameRepository.findAll().size();
        game.setAwayTeam("NewZeland");
        Game updatedGame = gameRepository.save(game);
        var finalSize = gameRepository.findAll().size();

        assertNotNull(updatedGame, "Updated game should not be null");
        assertEquals(game.getId(), updatedGame.getId(), "ID should not be changed");
        assertEquals(updatedGame, gameRepository.findById(updatedGame.getId()));
        assertEquals("NewZeland", gameRepository.findById(updatedGame.getId()).getAwayTeam(), "Away team should be updated");
        assertEquals(initialSize, finalSize, "Size of repository should remain the same after update");
    }

    @Test
    void findById() {
        Game game = makeGame();
        Game savedGame = gameRepository.save(game);
        Game foundGame = gameRepository.findById(savedGame.getId());
        assertNotNull(foundGame, "Found game should not be null");
        assertEquals(savedGame, foundGame, "Found game should match saved game");
    }

    @Test
    void findAll() {
        Game game1 = makeGame();
        Game game2 = makeGame();

        gameRepository.save(game1);
        gameRepository.save(game2);

        var allGames = gameRepository.findAll();
        assertFalse(allGames.isEmpty(), "All games should not be empty");
        assertEquals(2, allGames.size(), "There should be two games in the repository");
    }

    private Game makeGame() {
        return Game.builder()
                .homeTeam("Mexico")
                .awayTeam("Canada")
                .dateAdded(Instant.parse("2025-06-18T12:00:00Z"))
                .build();
    }
}
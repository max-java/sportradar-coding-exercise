package com.tutrit.sce.util;

import com.tutrit.sce.model.Game;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameFiltersTest {
    @Test
    void shouldReturnTrueWhenGameStartedAndFinished() {
        Game game = Game.builder()
                .endTime(Instant.now())
                .startTime(Instant.now())
                .build();

        assertTrue(GameFilters.gameStartedAndFinished(game));
    }

    @Test
    void shouldReturnFalseWhenGameNotStarted() {
        Game game = Game.builder().endTime(Instant.now()).build();
        assertFalse(GameFilters.gameStartedAndFinished(game));
    }

    @Test
    void shouldReturnFalseWhenGameNotFinished() {
        Game game = Game.builder().startTime(Instant.now()).build();
        assertFalse(GameFilters.gameStartedAndFinished(game));
    }

    @Test
    void shouldReturnFalseWhenGameNotStartedOrFinished() {
        Game game = Game.builder().build();
        assertFalse(GameFilters.gameStartedAndFinished(game));
    }

    @Test
    void shouldReturnTrueWhenGamesHaveSameId() {
        UUID id = UUID.randomUUID();
        Game left = Game.builder().id(id).build();
        Game right = Game.builder().id(id).build();

        assertTrue(GameFilters.gamesEqualById(left, right));
    }

    @Test
    void shouldReturnFalseWhenGamesHaveDifferentIds() {
        Game left = Game.builder().id(UUID.randomUUID()).build();
        Game right = Game.builder().id(UUID.randomUUID()).build();

        assertFalse(GameFilters.gamesEqualById(left, right));
    }
}

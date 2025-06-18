package com.tutrit.sce.util;

import com.tutrit.sce.model.Game;

public class GameFilters {

    private GameFilters() {
    }

    public static boolean gameStartedAndFinished(Game game) {
        return game.getStartTime() != null && game.getEndTime() != null;
    }

    public static boolean gamesEqualById(Game game1, Game game2) {
        return game1.getId().equals(game2.getId());
    }
}

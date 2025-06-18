package com.tutrit.sce.util;

import com.tutrit.sce.model.Game;

import java.util.Comparator;

public class GameComparators {

    private GameComparators() {
    }

    public static final Comparator<Game> BY_TOTAL_SCORE_DESC = Comparator
            .comparingInt((Game game) -> game.getHomeScore() + game.getAwayScore())
            .reversed();

    public static final Comparator<Game> BY_DATE_ADDED_DESC = Comparator
            .comparing(Game::getDateAdded)
            .reversed();
}

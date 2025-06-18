package com.tutrit.sce.util;

import com.tutrit.sce.model.Game;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameComparatorsTest {

    @Test
    void shouldSortByTotalScoreDescendingThenByDateDescending() {
        Game mexicoCanada = makeGame("Mexico", "Canada", 0, 5, Instant.parse("2025-06-18T10:00:00Z"));
        Game spainBrazil = makeGame("Spain", "Brazil", 10, 2, Instant.parse("2025-06-18T10:01:00Z"));
        Game germanyFrance = makeGame("Germany", "France", 2, 2, Instant.parse("2025-06-18T10:02:00Z"));
        Game uruguayItaly = makeGame("Uruguay", "Italy", 6, 6, Instant.parse("2025-06-18T10:03:00Z"));
        Game argentinaAustralia = makeGame("Argentina", "Australia", 3, 1, Instant.parse("2025-06-18T10:04:00Z"));

        List<Game> sorted = List.of(mexicoCanada, spainBrazil, germanyFrance, uruguayItaly, argentinaAustralia).stream()
                .sorted(GameComparators.BY_TOTAL_SCORE_DESC.thenComparing(GameComparators.BY_DATE_ADDED_DESC))
                .toList();

        // Expected order:
        // 1. Uruguay 6 - Italy 6       (total 12, added later)
        // 2. Spain 10 - Brazil 2       (total 12, added earlier)
        // 3. Mexico 0 - Canada 5       (total 5)
        // 4. Argentina 3 - Australia 1 (total 4, added later)
        // 5. Germany 2 - France 2      (total 4, added earlier)

        assertEquals(uruguayItaly, sorted.get(0));
        assertEquals(spainBrazil, sorted.get(1));
        assertEquals(mexicoCanada, sorted.get(2));
        assertEquals(argentinaAustralia, sorted.get(3));
        assertEquals(germanyFrance, sorted.get(4));
    }

    private Game makeGame(String homeTeam, String awayTeam, int homeScore, int awayScore, Instant dateAdded) {
        return Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeScore(homeScore)
                .awayScore(awayScore)
                .dateAdded(dateAdded)
                .build();
    }

}
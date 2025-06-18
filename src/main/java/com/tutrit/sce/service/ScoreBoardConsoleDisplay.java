package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ScoreBoardConsoleDisplay implements BoardDisplay {

    @Override
    public void showBoard(List<Game> scoreBoard) {
        List<Character> labels = IntStream.rangeClosed('a', 'z')
                .limit(scoreBoard.size())
                .mapToObj(i -> (char) i)
                .toList();

        String scoreboard = IntStream.range(0, scoreBoard.size())
                .boxed()
                .map(i -> String.format("%s. %s - %s: %d - %d",
                        labels.get(i),
                        scoreBoard.get(i).getHomeTeam(),
                        scoreBoard.get(i).getAwayTeam(),
                        scoreBoard.get(i).getHomeScore(),
                        scoreBoard.get(i).getAwayScore()))
                .collect(Collectors.joining("\n"));

        System.out.println("\n[Scoreboard]:\n" + scoreboard);

    }
}

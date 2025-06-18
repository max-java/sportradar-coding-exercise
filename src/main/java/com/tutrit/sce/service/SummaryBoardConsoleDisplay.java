package com.tutrit.sce.service;

import com.tutrit.sce.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SummaryBoardConsoleDisplay implements BoardDisplay {

    @Override
    public void showBoard(List<Game> board) {

        String scoreboard = IntStream.range(0, board.size())
                .boxed()
                .map(i -> String.format("%s. %s %d - %s %d",
                        i,
                        board.get(i).getHomeTeam(),
                        board.get(i).getHomeScore(),
                        board.get(i).getAwayTeam(),
                        board.get(i).getAwayScore()))
                .collect(Collectors.joining("\n"));

        System.out.println("\n[Summary board:]\n" + scoreboard);
    }
}

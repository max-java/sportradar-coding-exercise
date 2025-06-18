package com.tutrit.sce.repository;

import com.tutrit.sce.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRepository {
    private static final Map<UUID, Game> games = new ConcurrentHashMap<>() {
    };

    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(java.util.UUID.randomUUID());
            games.put(game.getId(), game);
            return game;
        } else {
            return updateGame(game);
        }
    }

    public Game findById(UUID id) {
        return games.get(id);
    }

    public List<Game> findAll() {
        return List.copyOf(games.values());
    }

    private Game updateGame(Game game) {
        return games.put(game.getId(), game);
    }
}

package duongnguyen.chess.core.service;

import duongnguyen.chess.core.driver.GameEventListener;
import duongnguyen.chess.core.driver.GameMaster;
import duongnguyen.chess.core.driver.GamePlayer;
import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.port.in.GameManagerPort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameManagerService implements GameManagerPort {
    private final Map<String, GameMaster> games = new HashMap<>();
    private final Map<String, GamePlayer> players = new HashMap<>();

    @Override
    public GameMaster createANewGame(String gameId) {
        if (!games.containsKey(gameId)) {
            var game = GameMaster.newGame();
            games.put(gameId, game);
            return game;
        }
        throw new IllegalArgumentException("Game with id " + gameId + " already exists");
    }

    @Override
    public GamePlayer playerJoinAGame(String gameId, String playerId) {
        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException("Game with id " + gameId + " does not exist");
        }
        if (players.containsKey(playerId)) {
            return players.get(playerId);
        }
        var game = games.get(gameId);
        var takenColor = players.values().stream()
                .map(GamePlayer::getColor)
                .toList();
        var availableColor = Arrays.stream(Color.values())
                .filter(color -> !takenColor.contains(color))
                .toList();
        if (availableColor.isEmpty()) {
            throw new IllegalStateException("Game with id " + gameId + " is full");
        }
        var player = new GamePlayer(game, availableColor.get(0));
        players.put(playerId, player);
        return player;
    }

    @Override
    public GamePlayer getPlayer(String playerId) {
        return players.get(playerId);
    }

    @Override
    public GameMaster getGame(String gameId) {
        return games.get(gameId);
    }

    @Override
    public void registerGameEventListener(String gameId, GameEventListener listener) {
        var game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game with id " + gameId + " does not exist");
        }
        game.registerGameEventListener(listener);
    }

    @Override
    public void unregisterGameEventListener(String gameId, GameEventListener listener) {
        var game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game with id " + gameId + " does not exist");
        }
        game.unregisterGameEventListener(listener);
    }
}

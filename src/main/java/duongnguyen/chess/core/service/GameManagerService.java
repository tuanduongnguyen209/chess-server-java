package duongnguyen.chess.core.service;

import duongnguyen.chess.core.driver.GameEventListener;
import duongnguyen.chess.core.driver.GameMaster;
import duongnguyen.chess.core.driver.GamePlayer;
import duongnguyen.chess.core.driver.GameSession;
import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.port.in.GameManagerPort;
import duongnguyen.chess.core.port.out.GameSessionsPort;

import java.util.*;

public class GameManagerService implements GameManagerPort, GameSessionsPort {
    private final Map<String, GameMaster> games = new HashMap<>();
    private final Map<String, GamePlayer> players = new HashMap<>();
    private final Map<String, String> playerGameMap = new HashMap<>();
    private final Map<String, List<String>> gamePlayerMap = new HashMap<>();

    @Override
    public void createANewGame(String gameId) {
        if (!games.containsKey(gameId)) {
            var game = GameMaster.newGame();
            games.put(gameId, game);
            return;
        }
        throw new IllegalArgumentException("Game with id " + gameId + " already exists");
    }

    @Override
    public void playerJoinAGame(String gameId, String playerId) {
        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException("Game with id " + gameId + " does not exist");
        }
        if (players.containsKey(playerId)) {
            players.get(playerId);
            return;
        }
        if (playerGameMap.containsKey(playerId)) {
            throw new IllegalStateException("Player with id " + playerId + " is already in a game");
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

        if (availableColor.size() == 1) {
            game.startGame();
        }

        players.put(playerId, player);
        playerGameMap.put(playerId, gameId);
        gamePlayerMap.computeIfAbsent(gameId, key -> new ArrayList<>()).add(playerId);
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

    @Override
    public List<GameSession> getGameSessions() {
        return games.keySet().stream()
                .map(gameMaster -> new GameSession(gameMaster, gamePlayerMap.get(gameMaster)))
                .toList();
    }
}

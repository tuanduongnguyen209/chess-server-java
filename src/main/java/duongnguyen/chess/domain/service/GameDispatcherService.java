package duongnguyen.chess.domain.service;

import duongnguyen.chess.domain.driver.GameEventListener;
import duongnguyen.chess.domain.driver.GameMaster;
import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.model.GameCommand;
import duongnguyen.chess.domain.model.GamePlayer;
import duongnguyen.chess.domain.port.in.GameDispatcherUseCase;

import java.util.*;

public class GameDispatcherService implements GameDispatcherUseCase {
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
        var player = new GamePlayer(availableColor.get(0));

        if (availableColor.size() == 1) {
            game.startGame();
        }

        players.put(playerId, player);
        playerGameMap.put(playerId, gameId);
        gamePlayerMap.computeIfAbsent(gameId, key -> new ArrayList<>()).add(playerId);
    }

    @Override
    public void playerSendCommand(GameCommand command) {
        var playerId = command.playerId();
        var gameId = playerGameMap.get(playerId);
        var game = games.get(gameId);
        var player = players.get(playerId);
        game.handleCommand(command, player.getColor());
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

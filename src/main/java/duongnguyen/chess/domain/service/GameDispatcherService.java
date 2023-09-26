package duongnguyen.chess.domain.service;

import duongnguyen.chess.domain.driver.GameEventListener;
import duongnguyen.chess.domain.driver.GameMaster;
import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.model.GameCommand;
import duongnguyen.chess.domain.model.GamePlayer;
import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.in.GameDispatcherUseCase;
import duongnguyen.chess.domain.port.out.PersistencePort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameDispatcherService implements GameDispatcherUseCase {
    private final PersistencePort persistencePort;
    private final Map<String, GameMaster> games = new HashMap<>();
    private final Map<String, Set<String>> playerGameMap = new HashMap<>();
    private final Map<String, Set<GamePlayer>> gamePlayerMap = new HashMap<>();

    public GameDispatcherService(PersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public void playerJoinAGame(String gameId, String playerId) {
        initializeGameIfNotInitialized(gameId);
        var game = games.get(gameId);
        var joinedGame = playerGameMap.get(playerId);
        var joinedPlayer = gamePlayerMap.get(gameId);

        // Player already joined the game
        if (joinedGame != null && joinedGame.contains(gameId)) {
            Color joinedColor = gamePlayerMap.get(gameId).stream()
                    .filter(player -> player.id().equals(playerId))
                    .findFirst()
                    .map(GamePlayer::getColor)
                    .orElseThrow();
            game.playerJoined(playerId, joinedColor);
            return;
        }

        var takenColor = joinedPlayer != null
                ? joinedPlayer.stream()
                    .map(GamePlayer::getColor)
                    .toList()
                : List.of();
        var availableColor = Arrays.stream(Color.values())
                .filter(color -> !takenColor.contains(color))
                .toList();
        if (availableColor.isEmpty()) {
            throw new IllegalStateException("Game with id " + gameId + " is full");
        }
        var player = new GamePlayer(playerId, availableColor.get(0));

        playerGameMap.computeIfAbsent(playerId, key -> new HashSet<>()).add(gameId);
        gamePlayerMap.computeIfAbsent(gameId, key -> new HashSet<>()).add(player);

        game.playerJoined(playerId, player.getColor());

        if (availableColor.size() == 1) {
            game.startGame();
        }
    }

    @Override
    public void playerSendCommand(GameCommand command) {
        var playerId = command.playerId();
        var gameId = command.gameId();
        var game = games.get(gameId);
        var player = gamePlayerMap.get(gameId).stream()
                .filter(p -> p.id().equals(playerId))
                .findFirst()
                .orElseThrow();
        game.handleCommand(command, player.getColor());
    }

    @Override
    public void registerGameEventListener(String gameId, GameEventListener listener) {
        initializeGameIfNotInitialized(gameId);
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

    private void initializeGameIfNotInitialized(String gameId) {
        if (!games.containsKey(gameId)) {
            GameSession gameSession = persistencePort.getGameSession(gameId);
            if (gameSession != null) {
                var game = GameMaster.newGame(gameId);
                games.put(gameId, game);
            } else
                throw new IllegalArgumentException("Game with id " + gameId + " does not exist");
        }
    }
}

package duongnguyen.chess.core.service;

import duongnguyen.chess.core.driver.GameMaster;
import duongnguyen.chess.core.driver.GamePlayer;
import duongnguyen.chess.core.port.in.CreateANewGameUseCase;
import duongnguyen.chess.core.port.in.PlayerJoinAGameUseCase;

import java.util.HashMap;
import java.util.Map;

public class GameManagerService implements CreateANewGameUseCase, PlayerJoinAGameUseCase {
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
        var player = new GamePlayer(game);
        players.put(playerId, player);
        game.registerGameEventListener(player);
        return player;
    }
}

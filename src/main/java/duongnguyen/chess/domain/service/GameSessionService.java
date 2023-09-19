package duongnguyen.chess.domain.service;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.in.GameDispatcherUseCase;
import duongnguyen.chess.domain.port.in.GameSessionUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameSessionService implements GameSessionUseCase {
    private final GameDispatcherUseCase gameDispatcher;

    public GameSessionService(GameDispatcherUseCase gameDispatcher) {
        this.gameDispatcher = gameDispatcher;
    }

    @Override
    public List<GameSession> getGameSessions() {
        return null;
    }

    @Override
    public GameSession getGameSession(String gameId) {
        return null;
    }

    @Override
    public GameSession createGameSession(String playerId) {
        String gameId = "game-" + System.currentTimeMillis();
        System.out.println("CREATE_A_NEW_GAME with ID: " + gameId);
        gameDispatcher.createANewGame(gameId);
        return new GameSession(gameId, List.of(playerId));
    }
}

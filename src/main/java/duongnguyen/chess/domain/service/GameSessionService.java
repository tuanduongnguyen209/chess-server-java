package duongnguyen.chess.domain.service;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.in.GameSessionUseCase;
import duongnguyen.chess.domain.port.out.PersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSessionService implements GameSessionUseCase {
    private final PersistencePort persistencePort;

    public GameSessionService(PersistencePort persistencePort) {
        this.persistencePort = persistencePort;
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
        var gameId = "game-" + System.currentTimeMillis();
        System.out.println("CREATE_A_NEW_GAME with ID: " + gameId);
        var gameSession = new GameSession(gameId, List.of(playerId));
        persistencePort.saveGameSession(gameSession);
        return new GameSession(gameId, List.of(playerId));
    }
}

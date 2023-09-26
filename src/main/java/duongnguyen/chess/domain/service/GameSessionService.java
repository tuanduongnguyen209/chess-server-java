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
        return persistencePort.getGameSessions();
    }

    @Override
    public GameSession getGameSession(String gameId) {
        return null;
    }

    @Override
    public GameSession createGameSession(String playerId) {
        return persistencePort.saveGameSession(List.of(playerId));
    }
}

package duongnguyen.chess.domain.port.out;

import duongnguyen.chess.domain.model.GameSession;

import java.util.List;

public interface PersistencePort {
    void saveGameSession(GameSession gameSession);
    List<GameSession> getGameSessions();
}

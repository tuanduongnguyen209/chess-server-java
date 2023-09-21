package duongnguyen.chess.domain.port.out;

import duongnguyen.chess.domain.model.GameSession;

public interface PersistencePort {
    void saveGameSession(GameSession gameSession);
}

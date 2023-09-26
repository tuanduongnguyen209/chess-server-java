package duongnguyen.chess.domain.port.out;

import duongnguyen.chess.domain.model.GameSession;

import java.util.List;

public interface PersistencePort {
    GameSession saveGameSession(List<String> playerIds);
    List<GameSession> getGameSessions();
    GameSession getGameSession(String gameId);
}

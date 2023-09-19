package duongnguyen.chess.domain.port.in;

import duongnguyen.chess.domain.model.GameSession;

import java.util.List;

public interface GameSessionUseCase {
    List<GameSession> getGameSessions();
    GameSession getGameSession(String gameId);
    GameSession createGameSession(String playerId);
}

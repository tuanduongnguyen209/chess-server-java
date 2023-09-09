package duongnguyen.chess.core.port.out;

import duongnguyen.chess.core.driver.GameSession;

import java.util.List;

public interface GameSessionsPort {
    List<GameSession> getGameSessions();
}

package duongnguyen.chess.controller;

import duongnguyen.chess.core.driver.GameSession;
import duongnguyen.chess.core.port.out.GameSessionsPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameSessionController {
    private final GameSessionsPort gameSessionsPort;

    public GameSessionController(GameSessionsPort gameSessionsPort) {
        this.gameSessionsPort = gameSessionsPort;
    }

    @GetMapping("/api/game-sessions")
    public List<GameSession> getGameSessions() {
        return gameSessionsPort.getGameSessions();
    }
}

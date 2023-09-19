package duongnguyen.chess.controller;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.in.GameSessionUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameSessionController {
    private final GameSessionUseCase gameSessionUseCase;

    public GameSessionController(GameSessionUseCase gameSessionUseCase) {
        this.gameSessionUseCase = gameSessionUseCase;
    }

    @GetMapping("/api/game-sessions")
    public List<GameSession> getGameSessions() {
        return gameSessionUseCase.getGameSessions();
    }

    @PostMapping("/api/game-sessions")
    public GameSession createGameSession(@RequestParam String playerId) {
        if (playerId == null || playerId.isBlank())
            throw new IllegalArgumentException("Player ID cannot be null or blank");
        return gameSessionUseCase.createGameSession(playerId);
    }
}

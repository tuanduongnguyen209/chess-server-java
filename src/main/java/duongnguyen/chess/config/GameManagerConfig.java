package duongnguyen.chess.config;

import duongnguyen.chess.domain.port.in.GameDispatcherUseCase;
import duongnguyen.chess.domain.port.in.GameSessionUseCase;
import duongnguyen.chess.domain.service.GameDispatcherService;
import duongnguyen.chess.domain.service.GameSessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameManagerConfig {
    private final GameDispatcherService gameDispatcherService;

    public GameManagerConfig() {
        this.gameDispatcherService = new GameDispatcherService();
    }

    @Bean
    public GameDispatcherUseCase gameManagerPort() {
        return gameDispatcherService;
    }
}
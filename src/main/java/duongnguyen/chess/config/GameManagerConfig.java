package duongnguyen.chess.config;

import duongnguyen.chess.core.port.in.GameManagerPort;
import duongnguyen.chess.core.port.out.GameSessionsPort;
import duongnguyen.chess.core.service.GameManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameManagerConfig {
    private final GameManagerService gameManagerService;

    public GameManagerConfig() {
        this.gameManagerService = new GameManagerService();
    }

    @Bean
    public GameManagerPort gameManagerPort() {
        return gameManagerService;
    }

    @Bean
    public GameSessionsPort gameSessionsPort() {
        return gameManagerService;
    }
}
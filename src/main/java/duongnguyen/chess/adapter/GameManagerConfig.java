package duongnguyen.chess.adapter;

import duongnguyen.chess.core.service.GameManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameManagerConfig {

    @Bean
    public GameManagerService gameManagerService() {
        return new GameManagerService();
    }
}
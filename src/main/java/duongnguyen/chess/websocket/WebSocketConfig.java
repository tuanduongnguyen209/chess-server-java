package duongnguyen.chess.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import duongnguyen.chess.adapter.ChessWebSocketAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {
    private final ChessWebSocketAdapter chessWebSocketAdapter;

    public WebSocketConfig(ChessWebSocketAdapter chessWebSocketAdapter) {
        this.chessWebSocketAdapter = chessWebSocketAdapter;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chessWebSocketAdapter, "/api/ws").setAllowedOrigins("*");
    }

    @Bean
    public ObjectMapper customObjectMapper() {
        return new ObjectMapper();
    }
}
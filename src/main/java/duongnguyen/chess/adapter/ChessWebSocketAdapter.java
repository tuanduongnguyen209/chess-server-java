package duongnguyen.chess.adapter;

import duongnguyen.chess.core.driver.GameMaster;
import duongnguyen.chess.core.port.in.CreateANewGamePort;
import duongnguyen.chess.core.port.in.PlayerJoinAGamePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChessWebSocketAdapter {

    private final CreateANewGamePort createANewGamePort;
    private final PlayerJoinAGamePort playerJoinAGamePort;
    private final WebSocketHandlerRegistry handlerRegistry;

    @Autowired
    public ChessWebSocketAdapter(CreateANewGamePort createANewGamePort, PlayerJoinAGamePort playerJoinAGamePort,
                                 WebSocketHandlerRegistry handlerRegistry) {
        this.createANewGamePort = createANewGamePort;
        this.playerJoinAGamePort = playerJoinAGamePort;
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    public void sendGameUpdate(String gameId, GameUpdate update) {
        // Get the game based on the provided gameId.
        GameMaster game = createANewGamePort.getGameById(gameId);
        if (game == null) {
            // Handle the case when the game does not exist or has ended.
            // For example, you can send an error message to the WebSocket client.
            return;
        }

        // Process the game update and send it to all WebSocket sessions associated with the game.
        TextMessage message = convertUpdateToTextMessage(update);
        for (WebSocketSession session : getSessionsByGameId(gameId)) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                // Handle the exception if the message fails to send.
                e.printStackTrace();
            }
        }
    }

    // Helper method to get all WebSocket sessions associated with a game ID.
    private List<WebSocketSession> getSessionsByGameId(String gameId) {
        List<WebSocketSession> gameSessions = new ArrayList<>();
        for (WebSocketSession session : handlerRegistry.getSessionMap().values()) {
            // Assuming you have a method in ChessWebSocketHandler to get game ID from a session.
            if (getGameIdFromSession(session).equals(gameId)) {
                gameSessions.add(session);
            }
        }
        return gameSessions;
    }

    // Helper method to convert the GameUpdate object to a TextMessage (if needed).
    private TextMessage convertUpdateToTextMessage(GameUpdate update) {
        // Convert GameUpdate object to a JSON string or any suitable format for WebSocket messages.
        // Use a JSON library like Jackson for serialization.
        // Return a new TextMessage with the JSON string as the payload.
    }
}

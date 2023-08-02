package duongnguyen.chess.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChessWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("New WebSocket connection established with session ID: " + sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // Assuming messages are in JSON format, you can parse the JSON here and handle different message types.
        // For example, you can use a JSON library like Jackson to handle JSON messages.

        // Example: Broadcast the received message to all connected clients.
        for (WebSocketSession s : sessions.values()) {
            s.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // WebSocket connection is closed. Perform any cleanup tasks here.
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("WebSocket connection closed with session ID: " + sessionId);
    }
}

package duongnguyen.chess.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import duongnguyen.chess.domain.port.in.GameDispatcherUseCase;
import duongnguyen.chess.webmodel.GameCommandWebModel;
import duongnguyen.chess.webmodel.GameEventWebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChessWebSocketAdapter extends TextWebSocketHandler {

    private final GameDispatcherUseCase gameDispatcher;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> sessionToPlayerId = new HashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public ChessWebSocketAdapter(GameDispatcherUseCase createANewGamePort, ObjectMapper objectMapper) {
        this.gameDispatcher = createANewGamePort;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("New WebSocket connection established with session ID: " + sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = session.getId();
        String payload = message.getPayload();
        System.out.println("Received message from session ID " + sessionId + ": " + payload);
        GameCommandWebModel gameCommand = objectMapper.readValue(payload, GameCommandWebModel.class);
        try {
            handleGameCommand(gameCommand, session);
        } catch (Exception e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage(e.getMessage()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("WebSocket connection closed with session ID: " + sessionId);
    }

    private void handleGameCommand(GameCommandWebModel command, WebSocketSession session) {
        switch (command.getType()) {
            case PLAYER_JOIN_A_GAME -> {
                if (command.getGameId() == null) {
                    throw new IllegalArgumentException("Game ID is required");
                }
                if (command.getPlayerId() == null) {
                    throw new IllegalArgumentException("Player ID is required");
                }
                if (sessionToPlayerId.containsKey(session.getId())) {
                    throw new IllegalArgumentException("Player already joined a game");
                }
                gameDispatcher.registerGameEventListener(command.getGameId(), event -> {
                    try {
                        var gameEventWebModel = GameEventWebModel.fromGameEvent(event);
                        String payload = objectMapper.writeValueAsString(gameEventWebModel);
                        session.sendMessage(new TextMessage(payload));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                gameDispatcher.playerJoinAGame(command.getGameId(), command.getPlayerId());
                sessionToPlayerId.put(session.getId(), command.getPlayerId());
            }
            case PLAYER_MOVE_A_PIECE -> {
                gameDispatcher.playerSendCommand(command.toGameCommand());
            }
            case PLAYER_LEAVE_A_GAME, PLAYER_CANCEL_DRAW, PLAYER_RESIGN_A_GAME, PLAYER_OFFER_DRAW, PLAYER_ACCEPT_DRAW, PLAYER_REJECT_DRAW -> {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        }
    }
}

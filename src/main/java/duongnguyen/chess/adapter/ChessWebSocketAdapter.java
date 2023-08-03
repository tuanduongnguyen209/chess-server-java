package duongnguyen.chess.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import duongnguyen.chess.core.model.GameCommand;
import duongnguyen.chess.core.model.GameCommandType;
import duongnguyen.chess.core.port.in.GameManagerPort;
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

    private final GameManagerPort gameManager;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> sessionToPlayerId = new HashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public ChessWebSocketAdapter(GameManagerPort createANewGamePort, ObjectMapper objectMapper) {
        this.gameManager = createANewGamePort;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
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
        handleGameCommand(gameCommand, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("WebSocket connection closed with session ID: " + sessionId);
    }

    private void handleGameCommand(GameCommandWebModel command, WebSocketSession session) {
        switch (command.getType()) {

            case CREATE_A_NEW_GAME -> {
                String gameId = "game-" + System.currentTimeMillis();
                gameManager.createANewGame(gameId);
                // Player auto join the game after creating it
                gameManager.playerJoinAGame(gameId, command.getPlayerId());
                sessionToPlayerId.put(session.getId(), command.getPlayerId());
            }
            case PLAYER_JOIN_A_GAME -> {
                gameManager.playerJoinAGame(command.getGameId(), command.getPlayerId());
                gameManager.registerGameEventListener(command.getGameId(), event -> {
                    try {
                        var gameEventWebModel = GameEventWebModel.fromGameEvent(event);
                        String payload = objectMapper.writeValueAsString(gameEventWebModel);
                        session.sendMessage(new TextMessage(payload));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                sessionToPlayerId.put(session.getId(), command.getPlayerId());
            }
            case PLAYER_MOVE_A_PIECE -> {
                var player = gameManager.getPlayer(command.getPlayerId());
                GameCommand gameCommand = new GameCommand(GameCommandType.MOVE,
                        new GameCommand.Move(
                                command.getFromX(),
                                command.getFromY(),
                                command.getToX(),
                                command.getToY()
                                ), player.getColor());
                player.sendGameCommand(gameCommand);
            }
            case PLAYER_LEAVE_A_GAME, PLAYER_CANCEL_DRAW, PLAYER_RESIGN_A_GAME, PLAYER_OFFER_DRAW, PLAYER_ACCEPT_DRAW, PLAYER_REJECT_DRAW -> {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        }
    }
}

package duongnguyen.chess.domain.driver;

import duongnguyen.chess.domain.board.BoardFacade;
import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.model.GameCommand;
import duongnguyen.chess.domain.model.GameEvent;
import duongnguyen.chess.domain.model.GameEventType;

import java.util.ArrayList;
import java.util.List;

public class GameMaster implements GameCommandHandler {
    private final List<GameEventListener> gameEventListeners;
    private final BoardFacade boardFacade;
    private final String gameId;

    private GameMaster(String gameId) {
        this.gameId = gameId;
        boardFacade = new BoardFacade();
        gameEventListeners = new ArrayList<>();
    }

    public static GameMaster newGame(String gameId) {
        return new GameMaster(gameId);
    }

    public void startGame() {
        boardFacade.initBoard();
        notifyGameStateChanged();
    }

    public void notifyGameStateChanged() {
        GameEvent event = new GameEvent(GameEventType.BOARD_STATE_CHANGED,
                boardFacade.getStatus(),
                boardFacade.getBoardState(),
                gameId);
        gameEventListeners.forEach(listener -> listener.handleEvent(event));
    }

    public void playerJoined(String playerId, Color playerColor) {
        GameEvent event = new GameEvent(GameEventType.PLAYER_JOINED,
                boardFacade.getStatus(),
                boardFacade.getBoardState(),
                gameId, playerId,
                playerColor);
        gameEventListeners.forEach(listener -> listener.handleEvent(event));
    }

    @Override
    public void handleCommand(GameCommand command, Color playerColor) {
        switch (command.type()) {
            case MOVE -> {
                var move = command.move();
                boardFacade.makeMove(move.fromX(), move.fromY(), move.toX(), move.toY(), playerColor);
                GameEvent event = new GameEvent(GameEventType.BOARD_STATE_CHANGED, boardFacade.getStatus(), boardFacade.getBoardState());
                gameEventListeners.forEach(listener -> listener.handleEvent(event));
            }
            case ACCEPT_DRAW, RESIGN -> {
            }
        }
    }

    public void registerGameEventListener(GameEventListener listener) {
        gameEventListeners.add(listener);
    }

    public void unregisterGameEventListener(GameEventListener listener) {
        gameEventListeners.remove(listener);
    }
}

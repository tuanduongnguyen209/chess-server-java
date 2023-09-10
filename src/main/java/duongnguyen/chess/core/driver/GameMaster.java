package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.board.BoardFacade;
import duongnguyen.chess.core.model.GameCommand;
import duongnguyen.chess.core.model.GameEvent;
import duongnguyen.chess.core.model.GameEventType;

import java.util.ArrayList;
import java.util.List;

public class GameMaster implements GameCommandHandler {
    private final List<GameEventListener> gameEventListeners;
    private final BoardFacade boardFacade;

    private GameMaster() {
        boardFacade = new BoardFacade();
        gameEventListeners = new ArrayList<>();
    }

    public static GameMaster newGame() {
        return new GameMaster();
    }

    public void startGame() {
        boardFacade.initBoard();
        GameEvent event = new GameEvent(GameEventType.BOARD_STATE_CHANGED, boardFacade.getStatus(), boardFacade.getBoardState());
        gameEventListeners.forEach(listener -> listener.handleEvent(event));
    }

    public void playerJoined() {
        GameEvent event = new GameEvent(GameEventType.PLAYER_JOINED, boardFacade.getStatus(), boardFacade.getBoardState());
        gameEventListeners.forEach(listener -> listener.handleEvent(event));
    }

    @Override
    public void handleCommand(GameCommand command) {
        switch (command.type()) {
            case MOVE -> {
                var move = command.move();
                boardFacade.makeMove(move.fromX(), move.fromY(), move.toX(), move.toY(), command.color());
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

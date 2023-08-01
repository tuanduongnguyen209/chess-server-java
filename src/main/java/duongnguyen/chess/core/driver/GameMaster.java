package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.board.BoardFacade;

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
}

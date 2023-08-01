package duongnguyen.chess.core.driver;

public class GamePlayer implements GameEventListener {
    private final GameCommandHandler commandHandler;

    public GamePlayer(GameCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void sendGameCommand(GameCommand command) {
        commandHandler.handleCommand(command);
    }

    @Override
    public void handleEvent(GameEvent event) {

    }
}

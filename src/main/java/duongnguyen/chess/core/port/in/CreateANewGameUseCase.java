package duongnguyen.chess.core.port.in;

import duongnguyen.chess.core.driver.GameMaster;

public interface CreateANewGameUseCase {
    GameMaster createANewGame(String gameId);
}

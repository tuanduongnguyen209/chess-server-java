package duongnguyen.chess.core.port.in;

import duongnguyen.chess.core.driver.GameMaster;

public interface CreateANewGamePort {
    GameMaster createANewGame(String gameId);
}

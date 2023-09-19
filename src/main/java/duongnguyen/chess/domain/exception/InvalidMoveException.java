package duongnguyen.chess.domain.exception;

public class InvalidMoveException extends IllegalArgumentException {
    public InvalidMoveException(String message) {
        super(message);
    }
}

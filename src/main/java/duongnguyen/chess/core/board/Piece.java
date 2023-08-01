package duongnguyen.chess.core.board;

import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.model.ImmutablePiece;
import duongnguyen.chess.exception.InvalidMoveException;

abstract class Piece {
    private final Color color;
    private final Board board;
    private int x;
    private int y;

    public Piece(Color color, int x, int y, Board board) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean isValidMove(int newX, int newY);

    public abstract String getSymbol();

    public ImmutablePiece toImmutablePiece() {
        return new ImmutablePiece(x, y, getSymbol(), getColor());
    }

    public Move moveTo(int x, int y) throws InvalidMoveException {
        if (!isValidMove(x, y)) {
            throw new InvalidMoveException("Invalid move");
        }

        Square from = board.getSquare(getX(), getY());
        Square to = board.getSquare(x, y);
        Piece capturedPiece = to.getPiece();
        Move move = new Move(from, to, this, capturedPiece);

        from.setPiece(null);
        to.setPiece(this);
        setX(x);
        setY(y);

        return move;
    }

    protected boolean isMoveWithinBounds(int newX, int newY) {
        return newX >= 0 && newX < Board.SIZE && newY >= 0 && newY < Board.SIZE;
    }

    protected Board getBoard() {
        return board;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }
}

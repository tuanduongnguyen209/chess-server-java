package duongnguyen.chess.core.board;

public class Queen extends SlidingPiece {
    public Queen (Color color, int x, int y, Board board) {
        super(color, x, y, board);
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        if (!isMoveWithinBounds(newX, newY)) {
            return false;
        }
        Board board = getBoard();
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        // Queen moves either horizontally (dx != 0, dy == 0),
        // vertically (dx == 0, dy != 0), or diagonally (dx == dy).
        if ((dx != 0 && dy == 0) || (dx == 0 && dy != 0) || (dx == dy)) {
            return isPathClear(newX, newY, board) &&
                    (board.getSquare(newX, newY).isEmpty() || board.getSquare(newX, newY).getPiece().getColor() != getColor());
        }

        return false;
    }
}

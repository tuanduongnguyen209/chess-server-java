package duongnguyen.chess.core.board;

public class Knight extends Piece {
    public Knight(Color color, int x, int y, Board board) {
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

        // A knight's move can be described as an L-shape: two squares in one direction and one square in the perpendicular direction.
        // Check if the move is an L-shape: (2, 1) or (1, 2).
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Square destinationSquare = board.getSquare(newX, newY);
            // The destination square must either be empty or have an opponent's piece to make a valid move.
            return destinationSquare.isEmpty() || destinationSquare.getPiece().getColor() != getColor();
        }

        return false;
    }
}

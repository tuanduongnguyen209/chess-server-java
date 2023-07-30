package duongnguyen.chess.core.board;

import duongnguyen.chess.exception.InvalidMoveException;

public class King extends Piece {
    private boolean hasMoved;

    public King(Color color, int x, int y, Board board) {
        super(color, x, y, board);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        if (!isMoveWithinBounds(newX, newY)) {
            return false;
        }
        Board board = getBoard();
        int currentX = getX();
        int currentY = getY();

        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        // A king can move one square in any direction (horizontally, vertically, or diagonally).
        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1) || (dx == 1 && dy == 1)) {
            Square destinationSquare = board.getSquare(newX, newY);
            // The destination square must either be empty or have an opponent's piece to make a valid move.
            return destinationSquare.isEmpty() || destinationSquare.getPiece().getColor() != getColor();
        }

        // Check for castling (special move for the King).
        if (isCastlingMove(newX, newY)) {
            return true;
        }

        return false;
    }

    @Override
    public Move moveTo(int newX, int newY) throws InvalidMoveException {
        Move move = super.moveTo(newX, newY);
        if (move != null) {
            setHasMoved(true);
        }
        if (isCastlingMove(newX, newY)) {
            int direction = (newX > getX()) ? 1 : -1;
            int rookX = (direction == 1) ? Board.SIZE - 1 : 0;
            int rookY = (getColor() == Color.WHITE) ? 0 : Board.SIZE - 1;
            Square rookSquare = getBoard().getSquare(rookX, rookY);
            Rook rook = (Rook) rookSquare.getPiece();
            rookSquare.setPiece(null);
            rookSquare.setPiece(rook);
            rook.setX(rookX + direction);
            rook.setHasMoved(true);
        }
        return move;
    }

    private boolean isCastlingMove(int newX, int newY) {
        int currentX = getX();
        Board board = getBoard();
        if (hasMoved()) {
            return false;
        }

        int direction = (newX > currentX) ? 1 : -1;
        int rookX = (direction == 1) ? Board.SIZE - 1 : 0;
        int rookY = (getColor() == Color.WHITE) ? 0 : Board.SIZE - 1;

        Square rookSquare = board.getSquare(rookX, rookY);
        if (rookSquare.getPiece() instanceof Rook rook) {
            return !rook.hasMoved() && isPathClearForCastling(newX, newY, direction, board);
        }
        return false;
    }

    private boolean isPathClearForCastling(int newX, int newY, int direction, Board board) {
        int currentX = getX();
        int currentY = getY();

        // Check if all the squares between the King's current position and the destination are empty.
        for (int x = currentX + direction; x != newX; x += direction) {
            if (!board.getSquare(x, newY).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

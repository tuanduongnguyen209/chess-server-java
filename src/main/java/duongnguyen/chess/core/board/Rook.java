package duongnguyen.chess.core.board;

import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.exception.InvalidMoveException;

class Rook extends SlidingPiece {
    private boolean hasMoved;
    public Rook (Color color, int x, int y, Board board) {
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
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        // Rook moves either horizontally (dx != 0, dy == 0) or vertically (dx == 0, dy != 0).
        if ((dx != 0 && dy == 0) || (dx == 0 && dy != 0)) {
            return isPathClear(newX, newY, board) &&
                    (board.getSquare(newX, newY).isEmpty() || board.getSquare(newX, newY).getPiece().getColor() != getColor());
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public Move moveTo(int x, int y) throws InvalidMoveException {
        Move move = super.moveTo(x, y);
        setHasMoved(true);
        return move;
    }
}

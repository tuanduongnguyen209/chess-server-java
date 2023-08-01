package duongnguyen.chess.core.board;

import duongnguyen.chess.core.model.Color;

class Bishop extends SlidingPiece {
    public Bishop(Color color, int x, int y, Board board) {
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

        // Bishop moves diagonally (dx == dy).
        if (dx == dy) {
            return isPathClear(newX, newY, board) &&
                    (board.getSquare(newX, newY).isEmpty() || board.getSquare(newX, newY).getPiece().getColor() != getColor());
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}

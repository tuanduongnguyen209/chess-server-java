package duongnguyen.chess.domain.board;

import duongnguyen.chess.domain.model.Color;

abstract class SlidingPiece extends Piece {
    public SlidingPiece(Color color, int x, int y, Board board) {
        super(color, x, y, board);
    }

    protected boolean isPathClear(int newX, int newY, Board board) {
        int currentX = getX();
        int currentY = getY();

        int dx = Math.abs(newX - currentX);
        int dy = Math.abs(newY - currentY);

        int xDir = Integer.compare(newX, currentX);
        int yDir = Integer.compare(newY, currentY);

        int steps = Math.max(dx, dy);
        for (int i = 1; i < steps; i++) {
            int intermediateX = currentX + i * xDir;
            int intermediateY = currentY + i * yDir;
            if (!board.getSquare(intermediateX, intermediateY).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}

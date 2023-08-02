package duongnguyen.chess.core.board;

import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.exception.InvalidMoveException;

class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(Color color, int x, int y, Board board) {
        super(color, x, y, board);
        this.hasMoved = false;
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
        int direction = getColor() == Color.WHITE ? 1 : -1;
        Square destinationSquare = board.getSquare(newX, newY);

        // Basic movement: one square forward
        if (newX == currentX && newY == currentY + direction) {
            return destinationSquare.isEmpty();
        }

        // Initial double move: two squares forward from starting position
        if (!hasMoved && newX == currentX && newY == currentY + 2 * direction) {
            Square intermediateSquare = board.getSquare(currentX, currentY + direction);
            return intermediateSquare.isEmpty() && destinationSquare.isEmpty();
        }

        // Capture diagonally (only if there's an opponent's piece on the destination square)
        if (Math.abs(newX - currentX) == 1
                && newY == currentY + direction
                && !destinationSquare.isEmpty()
                && destinationSquare.getPiece().getColor() != getColor()) {
            return true;
        }

        // En passant capture (special rule for pawns capturing diagonally after an opponent's double move)
        if (isEnPassantCaptureMove(newX, newY)) {
            return true;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    @Override
    public Move moveTo(int x, int y) throws InvalidMoveException {
        Move move = super.moveTo(x, y);
        setHasMoved(true);
        if (isEnPassantCaptureMove(x, y)) {
            Board board = getBoard();
            int direction = getColor() == Color.WHITE ? 1 : -1;
            Square capturedSquare = board.getSquare(x, y - direction);
            capturedSquare.setPiece(null);
            move.setCapturedPiece(capturedSquare.getPiece());
        }
        return move;
    }

    private boolean isEnPassantCaptureMove(int newX, int newY) {
        Board board = getBoard();
        int direction = getColor() == Color.WHITE ? 1 : -1;
        Move lastMove = board.getLastMove();

        return lastMove != null
                && lastMove.getPieceMoved() instanceof Pawn
                && lastMove.getTo().getX() == newX
                && lastMove.getTo().getY() == newY - direction
                && lastMove.getFrom().getY() == newY - 2 * direction
                && lastMove.getPieceMoved().getColor() != getColor();
    }
}

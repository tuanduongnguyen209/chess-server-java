package duongnguyen.chess.domain.board;

import duongnguyen.chess.domain.model.ImmutableMove;

class Move {
    private final Square from;
    private final Square to;
    private final Piece pieceMoved;
    private final Piece pieceCaptured;

    public Move(Square from, Square to, Piece pieceMoved, Piece pieceCaptured) {
        this.from = from;
        this.to = to;
        this.pieceMoved = pieceMoved;
        this.pieceCaptured = pieceCaptured;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public Piece getPieceCaptured() {
        return pieceCaptured;
    }

    public void setCapturedPiece(Piece piece) {
        to.setPiece(piece);
    }

    public ImmutableMove toImmutableMove() {
        return new ImmutableMove(from.getX(), from.getY(), to.getX(), to.getY(), pieceMoved.getSymbol(), pieceMoved.getColor());
    }
}

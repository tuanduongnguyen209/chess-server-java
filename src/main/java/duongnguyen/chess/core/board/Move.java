package duongnguyen.chess.core.board;

public class Move {
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
}

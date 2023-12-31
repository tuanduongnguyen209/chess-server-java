package duongnguyen.chess.domain.board;

import duongnguyen.chess.domain.model.BoardState;
import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.model.GameStatus;
import duongnguyen.chess.domain.exception.InvalidMoveException;

import java.util.List;

public class BoardFacade {
    private Board board;
    private Color turn = Color.WHITE;

    public void initBoard() {
        board = new Board();
        board.initializeBoard();
    }

    public BoardState getBoardState() {
        if (board == null) {
            return new BoardState(List.of(), List.of());
        }
        var pieces = List.of(board.getWhitePieces(), board.getBlackPieces())
                .stream()
                .flatMap(List::stream)
                .map(Piece::toImmutablePiece)
                .toList();
        var moves = board.getMoves()
                .stream()
                .map(Move::toImmutableMove)
                .toList();
        return new BoardState(pieces, moves);
    }

    public void makeMove(int fromX, int fromY, int toX, int toY, Color color) throws InvalidMoveException {
        if (turn != color) {
            throw new InvalidMoveException("It's not your turn");
        }
        var piece = board.getSquare(fromX, fromY).getPiece();
        if (piece == null) {
            throw new InvalidMoveException("There is no piece at the specified square");
        }
        if (piece.getColor() != color) {
            throw new InvalidMoveException("You can't move your opponent's piece");
        }
        board.movePiece(fromX, fromY, toX, toY);
        switchTurn();
    }

    private void switchTurn() {
        turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    public GameStatus getStatus() {
        return GameStatus.IN_PROGRESS;
    }
}

package duongnguyen.chess.domain.board;

import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.exception.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;

class Board {
    public static final int SIZE = 8;

    private final Square[][] squares;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final List<Move> moves;

    public Board() {
        squares = new Square[SIZE][SIZE];
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        moves = new ArrayList<>();
    }

    public void initializeBoard() {
        // Initialize all squares on the board
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                squares[x][y] = new Square(x, y);
            }
        }

        // Place white pieces
        placePiece(new Rook(Color.WHITE, 0, 0, this));
        placePiece(new Knight(Color.WHITE, 1, 0, this));
        placePiece(new Bishop(Color.WHITE, 2, 0, this));
        placePiece(new Queen(Color.WHITE, 3, 0, this));
        placePiece(new King(Color.WHITE, 4, 0, this));
        placePiece(new Bishop(Color.WHITE, 5, 0, this));
        placePiece(new Knight(Color.WHITE, 6, 0, this));
        placePiece(new Rook(Color.WHITE, 7, 0, this));

        for (int x = 0; x < SIZE; x++) {
            placePiece(new Pawn(Color.WHITE, x, 1, this));
        }

        // Place black pieces
        placePiece(new Rook(Color.BLACK, 0, 7, this));
        placePiece(new Knight(Color.BLACK, 1, 7, this));
        placePiece(new Bishop(Color.BLACK, 2, 7, this));
        placePiece(new Queen(Color.BLACK, 3, 7, this));
        placePiece(new King(Color.BLACK, 4, 7, this));
        placePiece(new Bishop(Color.BLACK, 5, 7, this));
        placePiece(new Knight(Color.BLACK, 6, 7, this));
        placePiece(new Rook(Color.BLACK, 7, 7, this));

        for (int x = 0; x < SIZE; x++) {
            placePiece(new Pawn(Color.BLACK, x, 6, this));
        }
    }

    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Move getLastMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.get(moves.size() - 1);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void placePiece(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        if (getSquare(x, y).isEmpty()) {
            getSquare(x, y).setPiece(piece);

            // Update the piece's list based on its color
            List<Piece> piecesList = piece.getColor() == Color.WHITE ? whitePieces : blackPieces;
            piecesList.add(piece);
        } else {
            throw new IllegalArgumentException("Square is already occupied");
        }
    }

    public void removePiece(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        if (!getSquare(x, y).isEmpty()) {
            if (getSquare(x, y).getPiece() == piece) {
                getSquare(x, y).setPiece(null);
            }

            // Update the piece's list based on its color
            List<Piece> piecesList = piece.getColor() == Color.WHITE ? whitePieces : blackPieces;
            piecesList.remove(piece);
        } else {
            throw new IllegalArgumentException("No piece to remove");
        }
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) throws InvalidMoveException {
        Piece piece = getSquare(fromX, fromY).getPiece();
        if (piece == null) {
            throw new InvalidMoveException("No piece to move");
        }

        Move move = piece.moveTo(toX, toY);
        moves.add(move);
        if (move.getPieceCaptured() != null) {
            removePiece(move.getPieceCaptured());
        }
    }
}

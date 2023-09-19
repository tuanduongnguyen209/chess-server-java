package duongnguyen.chess.domain.model;

import java.util.List;

public record BoardState(List<ImmutablePiece> pieces,
                         List<ImmutableMove> moves) {}

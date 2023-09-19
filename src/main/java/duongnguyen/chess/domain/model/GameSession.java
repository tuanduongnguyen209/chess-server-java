package duongnguyen.chess.domain.model;

import java.util.List;

public record GameSession(String gameId, List<String> playerIds) {
}

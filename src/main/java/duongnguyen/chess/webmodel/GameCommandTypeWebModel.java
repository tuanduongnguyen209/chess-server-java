package duongnguyen.chess.webmodel;

import duongnguyen.chess.domain.model.GameCommandType;

public enum GameCommandTypeWebModel {
    PLAYER_JOIN_A_GAME,
    PLAYER_LEAVE_A_GAME,
    PLAYER_MOVE_A_PIECE,
    PLAYER_RESIGN_A_GAME,
    PLAYER_OFFER_DRAW,
    PLAYER_ACCEPT_DRAW,
    PLAYER_REJECT_DRAW,
    PLAYER_CANCEL_DRAW;

    public GameCommandType toDomainModel() {
        return switch (this) {
            case PLAYER_MOVE_A_PIECE -> GameCommandType.MOVE;
            case PLAYER_RESIGN_A_GAME -> GameCommandType.RESIGN;
            case PLAYER_OFFER_DRAW -> GameCommandType.OFFER_DRAW;
            case PLAYER_ACCEPT_DRAW -> GameCommandType.ACCEPT_DRAW;
            default -> throw new IllegalArgumentException("Unknown GameCommandTypeWebModel: " + this);
        };
    }
}

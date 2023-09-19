package duongnguyen.chess.webmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import duongnguyen.chess.domain.model.GameCommand;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameCommandWebModel {
    @JsonProperty("type")
    private GameCommandTypeWebModel type;

    @JsonProperty("gameId")
    private String gameId;

    @JsonProperty("playerId")
    private String playerId;

    @JsonProperty("move")
    private String move;

    public GameCommandTypeWebModel getType() {
        return type;
    }

    public void setType(GameCommandTypeWebModel type) {
        this.type = type;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getMove() {
        return move;
    }

    public int getFromX() {
        return Integer.parseInt(move.split(",")[0]);
    }

    public int getFromY() {
        return Integer.parseInt(move.split(",")[1]);
    }

    public int getToX() {
        return Integer.parseInt(move.split(",")[2]);
    }

    public int getToY() {
        return Integer.parseInt(move.split(",")[3]);
    }

    public void setMove(String move) {
        this.move = move;
    }

    public GameCommand toGameCommand() {
        return new GameCommand(duongnguyen.chess.domain.model.GameCommandType.MOVE,
                new GameCommand.Move(
                        this.getFromX(),
                        this.getFromY(),
                        this.getToX(),
                        this.getToY()
                ), this.getPlayerId(), this.getGameId());
    }
}

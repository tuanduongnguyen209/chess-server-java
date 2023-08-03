package duongnguyen.chess.webmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import duongnguyen.chess.core.model.GameEvent;
import duongnguyen.chess.core.model.GameEventType;
import duongnguyen.chess.core.model.GameStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEventWebModel {

    @JsonProperty("type")
    private GameEventType type;

    @JsonProperty("status")
    private GameStatus status;

    @JsonProperty("boardState")
    private BoardStateWebModel boardState;

    public GameEventType getType() {
        return type;
    }

    public void setType(GameEventType type) {
        this.type = type;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public BoardStateWebModel getBoardState() {
        return boardState;
    }

    public void setBoardState(BoardStateWebModel boardState) {
        this.boardState = boardState;
    }

    public static GameEventWebModel fromGameEvent(GameEvent event) {
        GameEventWebModel gameEventWebModel = new GameEventWebModel();
        gameEventWebModel.setType(event.type());
        gameEventWebModel.setStatus(event.status());
        gameEventWebModel.setBoardState(BoardStateWebModel.fromBoardState(event.boardState()));
        return gameEventWebModel;
    }
}

package duongnguyen.chess.webmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.model.GameEvent;
import duongnguyen.chess.core.model.GameEventType;
import duongnguyen.chess.core.model.GameStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class GameEventWebModel {
    @JsonProperty("type")
    private GameEventType type;

    @JsonProperty("status")
    private GameStatus status;

    @JsonProperty("boardState")
    private BoardStateWebModel boardState;

    @JsonProperty("gameId")
    private String gameId;

    @JsonProperty("playerId")
    private String playerId;

    @JsonProperty("color")
    private Color color;

    public static GameEventWebModel fromGameEvent(GameEvent event) {
        return GameEventWebModel.builder()
                .type(event.type())
                .status(event.status())
                .playerId(event.playerId())
                .color(event.color())
                .boardState(BoardStateWebModel.fromBoardState(event.boardState()))
                .gameId(event.gameId())
                .build();
    }
}

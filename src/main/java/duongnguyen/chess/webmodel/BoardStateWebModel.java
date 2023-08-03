package duongnguyen.chess.webmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import duongnguyen.chess.core.model.BoardState;
import duongnguyen.chess.core.model.ImmutableMove;
import duongnguyen.chess.core.model.ImmutablePiece;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class BoardStateWebModel {

    @JsonProperty("pieces")
    private List<ImmutablePiece> pieces;

    @JsonProperty("moves")
    private List<ImmutableMove> moves;

    public static BoardStateWebModel fromBoardState(BoardState boardState) {
        BoardStateWebModel boardStateWebModel = new BoardStateWebModel();
        boardStateWebModel.setPieces(boardState.pieces());
        boardStateWebModel.setMoves(boardState.moves());
        return boardStateWebModel;
    }
}

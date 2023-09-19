package duongnguyen.chess.webmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import duongnguyen.chess.domain.model.BoardState;
import duongnguyen.chess.domain.model.ImmutableMove;
import duongnguyen.chess.domain.model.ImmutablePiece;
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

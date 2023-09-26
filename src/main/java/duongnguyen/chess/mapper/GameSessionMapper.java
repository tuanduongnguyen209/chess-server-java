package duongnguyen.chess.mapper;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.dto.GameSessionDto;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameSessionMapper {
    private final ModelMapper modelMapper;

    public GameSessionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GameSession toDomain(GameSessionDto dto) {
        var playerIds = dto.getPlayerIds() != null
                ? List.copyOf(dto.getPlayerIds())
                : new ArrayList<String>();
        return new GameSession(dto.getGameId(), playerIds);
    }

    public GameSessionDto toDto(GameSession domain) {
        return modelMapper.map(domain, GameSessionDto.class);
    }
}

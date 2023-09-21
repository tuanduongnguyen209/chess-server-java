package duongnguyen.chess.mapper;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.dto.GameSessionDto;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class GameSessionMapper {
    private final ModelMapper modelMapper;

    public GameSessionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GameSession toDomain(GameSessionDto dto) {
        return modelMapper.map(dto, GameSession.class);
    }

    public GameSessionDto toDto(GameSession domain) {
        return modelMapper.map(domain, GameSessionDto.class);
    }
}

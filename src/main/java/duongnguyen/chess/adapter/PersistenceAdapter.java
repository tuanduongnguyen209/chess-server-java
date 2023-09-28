package duongnguyen.chess.adapter;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.out.PersistencePort;
import duongnguyen.chess.mapper.GameSessionMapper;
import duongnguyen.chess.repository.GameSessionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Component
public class PersistenceAdapter implements PersistencePort {
    private final GameSessionMapper gameSessionMapper;
    private final GameSessionRepository gameSessionRepository;

    public PersistenceAdapter(GameSessionMapper gameSessionMapper, GameSessionRepository gameSessionRepository) {
        this.gameSessionMapper = gameSessionMapper;
        this.gameSessionRepository = gameSessionRepository;
    }

    @Override
    public GameSession saveGameSession(List<String> playerIds) {
        System.out.println("SAVE_GAME_SESSION");
        var dto = gameSessionRepository.save(gameSessionMapper.toDto(new GameSession(null, playerIds)));
        return gameSessionMapper.toDomain(dto);
    }

    @Override
    public List<GameSession> getGameSessions() {
        System.out.println("GET_GAME_SESSIONS");
        var sessions = gameSessionRepository.findAll();
        return StreamSupport.stream(sessions.spliterator(), false)
                .filter(Objects::nonNull)
                .map(gameSessionMapper::toDomain)
                .toList();
    }

    @Override
    public GameSession getGameSession(String gameId) {
        var dto = gameSessionRepository.findById(gameId);
        return dto.map(gameSessionMapper::toDomain).orElse(null);
    }
}

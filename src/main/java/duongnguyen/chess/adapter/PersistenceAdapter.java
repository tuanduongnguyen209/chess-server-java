package duongnguyen.chess.adapter;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.out.PersistencePort;
import duongnguyen.chess.dto.GameSessionDto;
import duongnguyen.chess.mapper.GameSessionMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PersistenceAdapter implements PersistencePort {
    private final RedisTemplate<String, GameSessionDto> redisTemplate;
    private final GameSessionMapper gameSessionMapper;

    public PersistenceAdapter(RedisTemplate<String, GameSessionDto> redisTemplate, GameSessionMapper gameSessionMapper) {
        this.redisTemplate = redisTemplate;
        this.gameSessionMapper = gameSessionMapper;
    }

    @Override
    public void saveGameSession(GameSession gameSession) {
        System.out.println("SAVE_GAME_SESSION: " + gameSession);
        redisTemplate.opsForValue().set("game-" + gameSession.gameId(), gameSessionMapper.toDto(gameSession));
    }

    @Override
    public List<GameSession> getGameSessions() {
        System.out.println("GET_GAME_SESSIONS");
        var list = redisTemplate.opsForValue().multiGet(Objects.requireNonNull(redisTemplate.keys("game-*")));
        assert list != null;
        return list.stream().map(gameSessionMapper::toDomain).toList();
    }
}

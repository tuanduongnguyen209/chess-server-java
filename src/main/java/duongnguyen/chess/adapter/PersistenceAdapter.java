package duongnguyen.chess.adapter;

import duongnguyen.chess.domain.model.GameSession;
import duongnguyen.chess.domain.port.out.PersistencePort;
import duongnguyen.chess.mapper.GameSessionMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersistenceAdapter implements PersistencePort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final GameSessionMapper gameSessionMapper;

    public PersistenceAdapter(RedisTemplate<String, Object> redisTemplate, GameSessionMapper gameSessionMapper) {
        this.redisTemplate = redisTemplate;
        this.gameSessionMapper = gameSessionMapper;
    }

    @Override
    public void saveGameSession(GameSession gameSession) {
        System.out.println("SAVE_GAME_SESSION: " + gameSession);
        redisTemplate.opsForValue().set(gameSession.gameId(), gameSessionMapper.toDto(gameSession));
    }
}

package duongnguyen.chess.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {
    private final JedisConnectionFactory jedisConnectionFactory;

    public RedisConfiguration() {
        this.jedisConnectionFactory = new JedisConnectionFactory();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return this.jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(this.jedisConnectionFactory);
        return redisTemplate;
    }

}

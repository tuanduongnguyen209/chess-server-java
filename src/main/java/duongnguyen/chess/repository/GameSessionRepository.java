package duongnguyen.chess.repository;

import duongnguyen.chess.dto.GameSessionDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends CrudRepository<GameSessionDto, String> {
}

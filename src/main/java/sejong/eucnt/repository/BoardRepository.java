package sejong.eucnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.entity.BoardEntity;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findById(Long id);
}

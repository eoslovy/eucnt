package sejong.eucnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.eucnt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(String id);

    UserEntity findByUserName(String userName);
}

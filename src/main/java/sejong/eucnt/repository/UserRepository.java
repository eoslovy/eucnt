package sejong.eucnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sejong.eucnt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserName(String userName);

    UserEntity findById(String id);
}

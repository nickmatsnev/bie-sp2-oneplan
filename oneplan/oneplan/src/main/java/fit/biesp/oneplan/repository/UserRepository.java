package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByNickname(String email);
    UserEntity findByEmail(String email);
    UserEntity findUserEntityBySecret(String secret);
    UserEntity findUserEntityById(Long id);

    UserEntity getById(long id);

    List<UserEntity> findAll();
}

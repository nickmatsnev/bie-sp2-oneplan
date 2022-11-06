package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.FriendEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<FriendEntity, Long> {
    FriendEntity findByNickname(String email);
    FriendEntity findByEmail(String email);

    List<FriendEntity> findAllByUserId(Integer userId);
}

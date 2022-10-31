package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<InvitationEntity, Integer> {

    InvitationEntity findInvitationEntityByInvitationId(int invitationId);

    List<InvitationEntity> findInvitationEntitiesByUserId(int userId);

    List<InvitationEntity> findAll();
}

package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.EventInvitationsEntity;
import fit.biesp.oneplan.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventInvitationsRepository extends CrudRepository<EventInvitationsEntity, Long> {
    List<EventInvitationsEntity> getAllByRecipientEmail(String email);
    EventInvitationsEntity getByRecipientEmailAndSender(String email, UserEntity sender);
}

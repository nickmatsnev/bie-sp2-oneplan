package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
    //EventEntity findByOrganiserId(Long organiserId);
    EventEntity findEvententityById(Long id);
}

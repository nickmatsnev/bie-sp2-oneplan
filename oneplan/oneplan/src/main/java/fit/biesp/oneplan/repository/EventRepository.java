package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
}

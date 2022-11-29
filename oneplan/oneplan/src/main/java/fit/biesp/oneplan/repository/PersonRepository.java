package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
    PersonEntity findByEmail(String email);
}

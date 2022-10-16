package fit.biesp.oneplan.repository;

import fit.biesp.oneplan.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
    PersonEntity findByEmail(String email);
}
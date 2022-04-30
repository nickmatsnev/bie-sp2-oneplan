package fit.biesp.oneplan.repository;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import fit.biesp.oneplan.entity.LocationEntity;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<LocationEntity, Long> {
    LocationEntity findByName(String name);
}

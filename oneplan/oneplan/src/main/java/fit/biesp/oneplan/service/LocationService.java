package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.exception.LocationAlreadyExistsException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public void postLocation(LocationEntity locationEntity) throws LocationAlreadyExistsException {
        if(locationRepository.findById(locationEntity.getId()).isPresent())
            throw new LocationAlreadyExistsException("Location already exists");
        locationRepository.save(locationEntity);
    }


    public LocationEntity getLocation(Long id) throws LocationIsMissingException {
        if (locationRepository.findById(id).isEmpty())
            throw new LocationIsMissingException("No location with id " + id + " exists");
        return locationRepository.findById(id).get();
    }

    public void deleteLocation(Long id) throws LocationIsMissingException {
        if (locationRepository.findById(id).isEmpty())
            throw new LocationIsMissingException("No location with id " + id + " exists");
        locationRepository.deleteById(id);
    }
}


package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.exception.LocationAlreadyExistsException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.LocationModel;
import fit.biesp.oneplan.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public LocationModel postLocation(LocationModel locationModel) throws LocationAlreadyExistsException {
        if(locationRepository.findByName(locationModel.getName()) != null)
            throw new LocationAlreadyExistsException("Location already exists");
        locationRepository.save(LocationModel.fromModel(locationModel));
        return LocationModel.toModel(locationRepository.findByName(locationModel.getName()));
    }


    public LocationModel getLocation(Long id) throws LocationIsMissingException {
        if (locationRepository.findById(id).isEmpty())
            throw new LocationIsMissingException("No location with id " + id + " exists");
        return LocationModel.toModel(locationRepository.findById(id).get());
    }


    public void deleteLocation(Long id) throws LocationIsMissingException {
        if (locationRepository.findById(id).isEmpty())
            throw new LocationIsMissingException("No location with id " + id + " exists");
        locationRepository.deleteById(id);
    }

    public List<EventModel> getEvents (String name) throws LocationIsMissingException {
        if (locationRepository.findByName(name) == null)
            throw new LocationIsMissingException("No location with name " + name + " exists");
        List<EventModel> res = new ArrayList<>();
        if (locationRepository.findByName(name).getEvents() != null)
            for (var event: locationRepository.findByName(name).getEvents())
                res.add(EventModel.toModel(event));

        return res;
    }

}


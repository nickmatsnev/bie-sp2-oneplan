package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.exception.LocationAlreadyExistsException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.LocationModel;
import fit.biesp.oneplan.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity postLocation(@RequestBody LocationModel locationModel) {
        try {
            locationService.postLocation(locationModel);
            return ResponseEntity.ok("New location posted successfully");
        } catch (LocationAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while posting a new location");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllLocations() {
        try {
            return ResponseEntity.ok(locationService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while trying to list locations. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocation (@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Location successfully deleted");
        } catch (LocationIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while deleting location");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getLocation(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(locationService.getLocation(id));
        } catch (LocationIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body("No such location found (id = " + id + ")");
        }
    }
}

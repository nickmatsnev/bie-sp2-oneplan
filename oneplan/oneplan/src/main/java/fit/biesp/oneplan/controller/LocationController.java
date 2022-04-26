package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.exception.LocationAlreadyExistsException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
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
    public ResponseEntity postLocation(@RequestBody LocationEntity locationEntity) {
        try {
            locationService.postLocation(locationEntity);
            return ResponseEntity.ok("New location posted successfully");
        } catch (LocationAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while posting a new location");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocation (Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Location successfully deleted");
        } catch (LocationIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while deleting location");
        }
    }

    @GetMapping()
    public ResponseEntity getLocation(Long id) {
        try {
            return ResponseEntity.ok(locationService.getLocation(id));
        } catch (LocationIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body("No such location found (id = " + id + ")");
        }
    }
}

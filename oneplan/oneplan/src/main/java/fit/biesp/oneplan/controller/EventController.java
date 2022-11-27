package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.exception.EventAlreadyExistsException;
import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.service.EventService;
import fit.biesp.oneplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventModel event) {
        try {
            System.out.println(event.getName());
            var message = eventService.createEvent(event);
            return ResponseEntity.ok("" + message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event creation error. " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(eventService.getEvent(id));
        } catch (EventIsMissingException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't get event");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@RequestBody EventModel eventModel, Long id) {
        try {
            eventService.updateEvent(eventModel, id);
            return ResponseEntity.ok("Event with id " + id + " has been updated successfully");
        } catch (EventIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't update event");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event with id " + id + " has been deleted successfully");
        } catch (EventIsMissingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Couldn't delete event");
        }
    }

//
//    @GetMapping("/user")
//    public ResponseEntity getUserEvents(@RequestBody Long organiserId) {
//        try {
//            return ResponseEntity.ok(EventService.getUserEvents(organiserId));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while trying to list events. " + e.getMessage());
//        }
//    }
}

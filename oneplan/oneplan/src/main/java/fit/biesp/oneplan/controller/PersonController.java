package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping()
    public ResponseEntity addPerson(@RequestBody PersonModel person){
        try{
            personService.addPerson(person);
            return ResponseEntity.ok("Person saved!");
        } catch (PersonAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(personService.getPerson(id));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePerson(@RequestBody PersonModel personModel, @PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(personService.updatePerson(personModel, id));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deletePerson(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(personService.delete(id));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }
}

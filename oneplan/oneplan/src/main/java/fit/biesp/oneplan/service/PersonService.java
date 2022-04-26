package fit.biesp.oneplan.service;

import fit.biesp.oneplan.exceptions.PersonAlreadyExistsException;
import fit.biesp.oneplan.exceptions.PersonNotFoundException;
import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    public PersonModel addPerson(PersonModel person) throws PersonAlreadyExistsException {
        if(personRepo.findByEmail(person.getEmail()) != null)
            throw new PersonAlreadyExistsException("Person with this email already exists!");

        return PersonModel.toModel(personRepo.save(PersonModel.fromModel(person)));
    }

    public PersonModel getPerson(Long id) throws PersonNotFoundException {
        if(personRepo.findById(id).isEmpty())
            throw new PersonNotFoundException("Person not found!");

        return PersonModel.toModel(personRepo.findById(id).get());
    }

    public PersonModel updatePerson(PersonModel gameModel, Long id) throws PersonNotFoundException {
        if(personRepo.findById(id).isEmpty())
            throw new PersonNotFoundException("Person not found!");

        return PersonModel.toModel(personRepo.save(PersonModel.fromModel(gameModel)));
    }

    public Long delete(Long id) throws PersonNotFoundException {
        if(personRepo.findById(id).isEmpty())
            throw new PersonNotFoundException("Game not found!");

        personRepo.deleteById(id);
        return id;
    }

}

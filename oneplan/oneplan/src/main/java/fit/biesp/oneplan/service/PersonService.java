package fit.biesp.oneplan.service;

import fit.biesp.oneplan.exception.PersonAlreadyExistsException;
import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public PersonModel addPerson(PersonModel person) throws PersonAlreadyExistsException {
        if(personRepository.findByEmail(person.getEmail()) != null)
            throw new PersonAlreadyExistsException("Person with this email already exists!");

        return PersonModel.toModel(personRepository.save(PersonModel.fromModel(person)));
    }

    public PersonModel getPerson(Long id) throws PersonNotFoundException {
        if(personRepository.findById(id).isEmpty())
            throw new PersonNotFoundException("Person not found!");

        return PersonModel.toModel(personRepository.findById(id).get());
    }

    public PersonModel updatePerson(PersonModel gameModel, Long id) throws PersonNotFoundException {
        if(personRepository.findById(id).isEmpty())
            throw new PersonNotFoundException("Person not found!");

        return PersonModel.toModel(personRepository.save(PersonModel.fromModel(gameModel)));
    }

    public Long delete(Long id) throws PersonNotFoundException {
        if(personRepository.findById(id).isEmpty())
            throw new PersonNotFoundException("Game not found!");

        personRepository.deleteById(id);
        return id;
    }

}

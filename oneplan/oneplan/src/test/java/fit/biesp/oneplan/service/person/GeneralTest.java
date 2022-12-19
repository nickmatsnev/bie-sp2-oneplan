package fit.biesp.oneplan.service.person;

import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.PersonModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest{
    @Test
    public void createPersonTest() {
        personRepository.save(this.person);
        Mockito.verify(personRepository).save(this.person);
        assertThat(this.person.getEmail()).isNotNull();
    }

    @Test
    public void deletePersonTest() throws PersonNotFoundException {
        Mockito.when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        personService.delete(person.getId());
        Mockito.verify(personRepository).deleteById(person.getId());
    }

    @Test
    public void updatePersonTest() {
        this.person.setEmail("updated");
        this.person = personRepository.save(this.person);

        Mockito.verify(personRepository).save(this.person);
        assertEquals(this.person.getEmail(), "updated");
    }

    @Test
    public void getPersonTest() {
        var savedPerson = personRepository.save(person);
        if(savedPerson.equals(person))
            Mockito.when(PersonModel.fromModel(PersonModel.toModel(person))).thenReturn(savedPerson);

        var getResult = PersonModel.fromModel(PersonModel.toModel(person));
        assertEquals(getResult.getEmail(), savedPerson.getEmail());
    }
}

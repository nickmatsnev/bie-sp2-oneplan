package fit.biesp.oneplan.service.person;

import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.PersonModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SafetyTest extends BaseTest{
    @Test
    public void createInvalidPersonTest() {
        personRepository.save(null);
        Mockito.verify(personRepository).save(null);
    }

    @Test
    public void deleteInvalidPersonTest() throws PersonNotFoundException {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> personService.delete(person.getId()));
    }

    @Test
    public void updateMissingPersonTest() {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> personService.updatePerson(PersonModel.toModel(person), person.getId()));
    }

    @Test
    public void getMissingPersonTest() {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> personService.getPerson(person.getId()));
    }
}

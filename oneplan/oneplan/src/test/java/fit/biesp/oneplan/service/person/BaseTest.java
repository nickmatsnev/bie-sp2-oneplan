package fit.biesp.oneplan.service.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.repository.PersonRepository;
import fit.biesp.oneplan.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BaseTest {
    @Autowired
    protected PersonService personService;
    @MockBean
    protected PersonRepository personRepository;

    protected PersonEntity person;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.person = new PersonEntity(
                "email");
        this.objectMapper = new ObjectMapper();
        Mockito.when(personRepository.save(person)).thenReturn(person);
    }
}

package fit.biesp.oneplan.controller.person;

import fit.biesp.oneplan.controller.PersonController;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.service.PersonService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(PersonController.class)
public class BaseTest {
    @Autowired
    protected MockMvc personMvc;
    @MockBean
    protected PersonService personService;

    protected ObjectMapper objectMapper;
    protected PersonEntity personEntity;
    protected PersonModel personModel;

    protected PersonEntity updatedPersonEntity;
    protected PersonModel updatedPersonModel;

    @BeforeEach
    public void initialise() {
        this.personEntity = new PersonEntity(
                "email");
        this.updatedPersonEntity = new PersonEntity(
                "updatedEmail");
        this.updatedPersonModel = PersonModel.toModel(updatedPersonEntity);
        this.personModel = PersonModel.toModel(personEntity);
        this.objectMapper = new ObjectMapper();
    }

}

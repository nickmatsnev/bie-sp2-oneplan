package fit.biesp.oneplan.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.repository.UserRepository;
import fit.biesp.oneplan.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BaseTest {
    @Autowired
    protected UserService userService;
    @MockBean
    protected UserRepository userRepository;

    protected UserEntity user;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.user = new UserEntity("name",
                "email",
                "password");
        this.objectMapper = new ObjectMapper();
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }
}

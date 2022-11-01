package fit.biesp.oneplan.controller.user;

import fit.biesp.oneplan.controller.UserController;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import fit.biesp.oneplan.service.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class BaseTest {
    @Autowired
    protected MockMvc userMvc;
    @MockBean
    protected UserService userService;

    protected ObjectMapper objectMapper;
    protected UserEntity userEntity;
    protected UserModel userModel;
    protected UserRegistrationModel userRegistrationModel;

    protected UserEntity updatedUserEntity;
    protected UserModel updatedUserModel;

    @BeforeEach
    public void initialise() {
        this.userEntity = new UserEntity(
                "name", "email", "password");
        this.updatedUserEntity = new UserEntity(
                "updatedName","updatedEmail", "updatedPassword");
        this.updatedUserModel = UserModel.toModel(updatedUserEntity);
        this.userModel = UserModel.toModel(userEntity);
        this.userRegistrationModel = UserRegistrationModel.toModel(userEntity);
        this.objectMapper = new ObjectMapper();
    }
}

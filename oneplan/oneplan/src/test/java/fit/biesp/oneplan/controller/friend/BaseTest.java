package fit.biesp.oneplan.controller.friend;

import fit.biesp.oneplan.controller.FriendController;
import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.service.FriendService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FriendController.class)
public class BaseTest {

    @Autowired
    protected MockMvc friendMvc;
    @MockBean
    protected FriendService friendService;

    protected ObjectMapper objectMapper;
    protected FriendEntity friendEntity;
    protected FriendModel friendModel;

    protected FriendEntity updatedFriendEntity;
    protected FriendModel updatedFriendModel;

    @BeforeEach
    public void initialise() {
        this.friendEntity = new FriendEntity(
                "name", "email");
        this.updatedFriendEntity = new FriendEntity(
                "updatedName","updatedEmail");
        this.updatedFriendModel = FriendModel.toModel(updatedFriendEntity);
        this.friendModel = FriendModel.toModel(friendEntity);
        this.objectMapper = new ObjectMapper();
    }
}

package fit.biesp.oneplan.service.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.repository.FriendRepository;
import fit.biesp.oneplan.service.FriendService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BaseTest {
    @Autowired
    protected FriendService friendService;
    @MockBean
    protected FriendRepository friendRepository;

    protected FriendEntity friend;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.friend = new FriendEntity("email",
                "name");
        this.objectMapper = new ObjectMapper();
        Mockito.when(friendRepository.save(friend)).thenReturn(friend);
    }
}

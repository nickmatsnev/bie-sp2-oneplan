package fit.biesp.oneplan.service.friend;

import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.FriendModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SafetyTest extends BaseTest {
    @Test
    public void createInvalidFriendTest() {
        friendRepository.save(null);
        Mockito.verify(friendRepository).save(null);
    }

    @Test
    public void deleteInvalidFriendTest() throws PersonNotFoundException {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> friendService.delete(friend.getNickname()));
    }

    @Test
    public void updateMissingFriendTest() {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> friendService.updateFriend(FriendModel.toModel(friend), friend.getNickname()));
    }

    @Test
    public void getMissingFriendTest() {
        Assertions.assertThrows(PersonNotFoundException.class,
                () -> friendService.getFriend(friend.getNickname()));
    }
}

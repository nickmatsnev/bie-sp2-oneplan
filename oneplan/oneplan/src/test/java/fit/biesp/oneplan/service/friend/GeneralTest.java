package fit.biesp.oneplan.service.friend;

import fit.biesp.oneplan.exception.PersonNotFoundException;
import fit.biesp.oneplan.model.FriendModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {
    @Test
    public void createFriendTest() {
        friendRepository.save(this.friend);
        Mockito.verify(friendRepository).save(this.friend);
        assertThat(this.friend.getNickname()).isNotNull();
    }

    @Test
    public void deleteFriendTest() throws PersonNotFoundException {
        Mockito.when(friendRepository.findByNickname(friend.getNickname())).thenReturn(friend);

        friendService.delete(friend.getNickname());
        Mockito.verify(friendRepository).deleteById(friend.getId());
    }

    @Test
    public void updateFriendTest() {
        this.friend.setEmail("updated");
        this.friend = friendRepository.save(this.friend);

        Mockito.verify(friendRepository).save(this.friend);
        assertEquals(this.friend.getEmail(), "updated");
    }

    @Test
    public void getFriendTest() {
        var savedPerson = friendRepository.save(friend);
        if(savedPerson.equals(friend))
            Mockito.when(FriendModel.fromModel(FriendModel.toModel(friend))).thenReturn(savedPerson);

        var getResult = FriendModel.fromModel(FriendModel.toModel(friend));
        assertEquals(getResult.getEmail(), savedPerson.getEmail());
    }
}

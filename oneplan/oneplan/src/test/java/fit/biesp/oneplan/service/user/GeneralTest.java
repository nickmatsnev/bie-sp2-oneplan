package fit.biesp.oneplan.service.user;

import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.UserModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {
    @Test
    public void createUserTest() {
        userRepository.save(this.user);
        Mockito.verify(userRepository).save(this.user);
        assertThat(this.user.getNickname()).isNotNull();
    }

    @Test
    public void deleteUserTest() throws UserNotFoundException {
        Mockito.when(userRepository.findByNickname(user.getNickname())).thenReturn(user);

        userService.delete(user.getNickname());
        Mockito.verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void updateUserTest() {
        this.user.setEmail("updated");
        this.user = userRepository.save(this.user);

        Mockito.verify(userRepository).save(this.user);
        assertEquals(this.user.getEmail(), "updated");
    }

    @Test
    public void getUserTest() {
        var savedPerson = userRepository.save(user);
        if(savedPerson.equals(user))
            Mockito.when(UserModel.fromModel(UserModel.toModel(user))).thenReturn(savedPerson);

        var getResult = UserModel.fromModel(UserModel.toModel(user));
        assertEquals(getResult.getEmail(), savedPerson.getEmail());
    }
}

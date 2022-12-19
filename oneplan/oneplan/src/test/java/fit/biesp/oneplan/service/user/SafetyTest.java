package fit.biesp.oneplan.service.user;

import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SafetyTest extends BaseTest {
    @Test
    public void createInvalidUserTest() {
        userRepository.save(null);
        Mockito.verify(userRepository).save(null);
    }

    @Test
    public void deleteInvalidUserTest() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.delete(user.getNickname()));
    }

    @Test
    public void updateMissingUserTest() {
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(UserModel.toModel(user), user.getNickname()));
    }

    @Test
    public void getMissingUserTest() {
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.getUser(user.getNickname()));
    }
}

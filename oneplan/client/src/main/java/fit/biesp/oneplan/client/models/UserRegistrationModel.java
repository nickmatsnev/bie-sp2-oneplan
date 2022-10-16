package fit.biesp.oneplan.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationModel {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String passwordConfirm;
}

package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.UserEntity;
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

    public static UserRegistrationModel toModel(UserEntity entity){
        UserRegistrationModel model = new UserRegistrationModel();
        model.setId(entity.getId());
        model.setNickname(entity.getNickname());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        return model;
    }

    public static UserEntity fromModel(UserRegistrationModel registrationModel){
        UserEntity entity = new UserEntity();

        entity.setId(registrationModel.getId());
        entity.setNickname(registrationModel.getNickname());
        entity.setEmail(registrationModel.getEmail());
        entity.setPassword(registrationModel.getPassword());
        return entity;
    }

}

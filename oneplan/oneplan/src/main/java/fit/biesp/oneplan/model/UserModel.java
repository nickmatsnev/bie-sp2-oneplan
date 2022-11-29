package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends PersonModel{
    private Long id;
    private String nickname;
    private String email;

    public static UserModel toModel(UserEntity entity){
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setNickname(entity.getNickname());
        model.setEmail(entity.getEmail());
        return model;
    }

    public static UserEntity fromModel(UserModel model){
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setNickname(model.getNickname());
        entity.setEmail(model.getEmail());
        return entity;
    }
}

package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.FriendEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendModel {
    private Long id;
    private String nickname;
    private String email;
    private int userId;

    public static FriendModel toModel(FriendEntity entity){
        FriendModel model = new FriendModel();
        model.setId(entity.getId());
        model.setNickname(entity.getNickname());
        model.setEmail(entity.getEmail());
        model.setUserId(entity.getUserId());
        return model;
    }

    public static FriendEntity fromModel(FriendModel model){
        FriendEntity entity = new FriendEntity();
        entity.setId(model.getId());
        entity.setNickname(model.getNickname());
        entity.setEmail(model.getEmail());
        entity.setUserId(model.getUserId());
        return entity;
    }
}

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
public class UserModelWithSecret {
    private String email;
    private String secret;
}

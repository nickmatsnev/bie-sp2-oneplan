package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {
    private Long id;
    private String email;

    public static PersonModel toModel(PersonEntity entity){
        PersonModel model = new PersonModel();
        model.setId(entity.getId());
        model.setEmail(entity.getEmail());
        return model;
    }

    public static PersonEntity fromModel(PersonModel model){
        PersonEntity entity = new PersonEntity();
        entity.setId(model.getId());
        entity.setEmail(model.getEmail());
        return entity;
    }
}

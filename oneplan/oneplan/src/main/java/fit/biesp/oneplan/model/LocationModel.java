package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.LocationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {
    private Long id;
    private String name, address, coordinates;

    public static LocationModel toModel(LocationEntity locationEntity) {
        var model = new LocationModel();
        model.setId(locationEntity.getId());
        model.setName(locationEntity.getName());
        model.setAddress(locationEntity.getAddress());
        model.setCoordinates(locationEntity.getCoordinates());

        return model;
    }

    public static LocationEntity fromModel(LocationModel locationModel) {
        var entity = new LocationEntity();
        entity.setId(locationModel.getId());
        entity.setName(locationModel.getName());
        entity.setAddress(locationModel.getAddress());
        entity.setCoordinates(locationModel.getCoordinates());

        return entity;
    }
}

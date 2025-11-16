package api.dto;

import core.enums.LocationType;

public class LocationCreateDTO {
    public LocationType locationType;

    public LocationCreateDTO() {}

    public LocationCreateDTO(LocationType locationType) {
        this.locationType = locationType;
    }
}

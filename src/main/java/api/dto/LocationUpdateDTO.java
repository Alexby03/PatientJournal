package api.dto;

import core.enums.LocationType;

public class LocationUpdateDTO {
    public LocationType locationType;

    public LocationUpdateDTO() {}

    public LocationUpdateDTO(LocationType locationType) {
        this.locationType = locationType;
    }
}

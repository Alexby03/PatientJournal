package api.dto;

import core.enums.LocationType;

public class LocationCreateDTO {
    public LocationType locationType;

    public LocationCreateDTO() {}

    public LocationCreateDTO(LocationType locationType) {
        this.locationType = locationType;
    }

    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }
}

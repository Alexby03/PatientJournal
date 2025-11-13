package core.mappers;

import api.dto.LocationDTO;
import data.entities.Location;

public class LocationMapper {

    public static LocationDTO toDTO(Location location) {
        if (location == null) return null;
        return new LocationDTO(
                location.getLocationId(),
                location.getLocationType().name()
        );
    }
}

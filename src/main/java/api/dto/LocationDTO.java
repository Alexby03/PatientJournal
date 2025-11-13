package api.dto;

import java.util.UUID;

public class LocationDTO {

    public UUID locationId;
    public String locationType;

    public LocationDTO(UUID locationId, String locationType) {
        this.locationId = locationId;
        this.locationType = locationType;
    }
}

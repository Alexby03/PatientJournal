package api.dto;

import core.enums.OrganizationType;
import java.util.UUID;

public class LocationDTO {
    public UUID locationId;
    public String locationType;

    public LocationDTO() {}

    public LocationDTO(UUID locationId, String locationType) {
        this.locationId = locationId;
        this.locationType = locationType;
    }
}

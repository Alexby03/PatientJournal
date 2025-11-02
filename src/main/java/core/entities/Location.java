package core.entities;

import core.enums.LocationType;

import java.util.UUID;

public record Location(UUID locationId, LocationType locationType) {};

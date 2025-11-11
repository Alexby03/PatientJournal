package core.services;

import data.entities.Location;
import data.repositories.LocationRepository;
import api.dto.LocationCreateDTO;
import api.dto.LocationUpdateDTO;
import core.enums.LocationType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LocationService {

    @Inject
    LocationRepository locationRepository;

    /**
     * Get all locations
     */
    public Uni<List<Location>> getAllLocations() {
        return locationRepository.findAllLocations();
    }

    /**
     * Get location by ID
     */
    public Uni<Location> getLocationById(UUID locationId) {
        return locationRepository.findById(locationId);
    }

    /**
     * Create a new location from DTO
     */
    public Uni<Location> createLocation(LocationCreateDTO dto) {
        if (dto.getLocationType() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Location type is required"));
        }

        Location location = new Location(dto.getLocationType());
        return locationRepository.persist(location);
    }

    /**
     * Update location information from DTO
     */
    public Uni<Location> updateLocation(UUID locationId, LocationUpdateDTO dto) {
        return locationRepository.findById(locationId)
                .chain(location -> {
                    if (location == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Location not found"));
                    }

                    if (dto.getLocationType() != null) {
                        location.setLocationType(dto.getLocationType());
                    }

                    return locationRepository.persist(location);
                });
    }

    /**
     * Get locations by type
     */
    public Uni<List<Location>> getLocationsByType(LocationType locationType) {
        return locationRepository.findByLocationType(locationType);
    }

    /**
     * Count total locations
     */
    public Uni<Long> countLocations() {
        return locationRepository.countTotalLocations();
    }
}

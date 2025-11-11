package data.repositories;

import data.entities.Location;
import core.enums.LocationType;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LocationRepository implements PanacheRepository<Location> {

    /**
     * Find locations by type
     */
    public Uni<List<Location>> findByLocationType(LocationType locationType) {
        return find("locationType", locationType).list();
    }

    /**
     * Get all locations
     */
    public Uni<List<Location>> findAllLocations() {
        return findAll().list();
    }

    /**
     * Count total locations
     */
    public Uni<Long> countTotalLocations() {
        return count();
    }
}

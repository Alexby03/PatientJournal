package data.repositories;

import data.entities.Organization;
import core.enums.OrganizationType;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrganizationRepository implements PanacheRepositoryBase<Organization, UUID> {

    /**
     * Find organizations by type
     */
    public Uni<List<Organization>> findByOrganizationType(OrganizationType organizationType) {
        return find("organizationType", organizationType).list();
    }

    /**
     * Get all organizations with pagination
     */
    public Uni<List<Organization>> findAllOrganizations(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    /**
     * Count total organizations
     */
    public Uni<Long> countTotalOrganizations() {
        return count();
    }
}

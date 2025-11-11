package data.repositories;

import data.entities.Practitioner;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PractitionerRepository implements PanacheRepository<Practitioner> {

    /**
     * Find practitioner by email
     */
    public Uni<Practitioner> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Find all practitioners by organization ID
     */
    public Uni<List<Practitioner>> findByOrganizationId(UUID organizationId) {
        return find("organization.organizationId", organizationId).list();
    }

    /**
     * Get all practitioners with pagination
     */
    public Uni<List<Practitioner>> findAllPractitioners(int pageIndex, int pageSize) {
        return findAll().page(pageIndex, pageSize).list();
    }

    /**
     * Count practitioners in an organization
     */
    public Uni<Long> countByOrganization(UUID organizationId) {
        return count("organization.organizationId", organizationId);
    }

    /**
     * Search practitioners by full name
     */
    public Uni<List<Practitioner>> searchByName(String namePattern) {
        return find("fullName like ?1", "%" + namePattern + "%").list();
    }

    /**
     * Delete practitioner by email
     */
    public Uni<Boolean> deletePractitionerByEmail(String email) {
        return delete("email", email).map(count -> count > 0);
    }
}

package data.repositories;

import data.entities.Practitioner;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PractitionerRepository implements PanacheRepositoryBase<Practitioner, UUID> {

    /**
     * Find practitioner by email
     */
    public Uni<Practitioner> findByEmail(String email) {
        // Praktikanter har alltid en organization, filtrerar på email
        return find("email = ?1", email).firstResult();
    }

    /**
     * Find practitioners by organization
     */
    public Uni<List<Practitioner>> findByOrganizationId(UUID organizationId) {
        // JPQL/HQL använder entitetsfält: organization.organizationId
        return find("organization.organizationId = ?1", organizationId).list();
    }

    /**
     * Count practitioners in an organization
     */
    public Uni<Long> countByOrganization(UUID organizationId) {
        return count("organization.organizationId = ?1", organizationId);
    }
}

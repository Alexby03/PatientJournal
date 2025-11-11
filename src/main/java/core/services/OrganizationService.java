package core.services;

import data.entities.Organization;
import data.entities.Location;
import data.repositories.OrganizationRepository;
import data.repositories.LocationRepository;
import api.dto.OrganizationCreateDTO;
import api.dto.OrganizationUpdateDTO;
import core.enums.OrganizationType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrganizationService {

    @Inject
    OrganizationRepository organizationRepository;

    @Inject
    LocationRepository locationRepository;

    /**
     * Get all organizations with pagination
     */
    public Uni<List<Organization>> getAllOrganizations(int pageIndex, int pageSize) {
        return organizationRepository.findAllOrganizations(pageIndex, pageSize);
    }

    /**
     * Get organization by ID
     */
    public Uni<Organization> getOrganizationById(UUID organizationId) {
        return organizationRepository.findById(organizationId);
    }

    /**
     * Create a new organization from DTO
     */
    public Uni<Organization> createOrganization(OrganizationCreateDTO dto) {
        if (dto.getOrganizationType() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Organization type is required"));
        }
        if (dto.getLocationId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Location ID is required"));
        }

        return locationRepository.findById(dto.getLocationId())
                .chain(location -> {
                    if (location == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Location not found"));
                    }

                    Organization organization = new Organization(
                            dto.getOrganizationType(),
                            location
                    );

                    return organizationRepository.persist(organization);
                });
    }

    /**
     * Update organization information from DTO
     */
    public Uni<Organization> updateOrganization(UUID organizationId, OrganizationUpdateDTO dto) {
        return organizationRepository.findById(organizationId)
                .chain(organization -> {
                    if (organization == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Organization not found"));
                    }

                    if (dto.getOrganizationType() != null) {
                        organization.setOrganizationType(dto.getOrganizationType());
                    }

                    if (dto.getLocationId() != null) {
                        return locationRepository.findById(dto.getLocationId())
                                .chain(location -> {
                                    if (location == null) {
                                        return Uni.createFrom().failure(
                                                new IllegalArgumentException("Location not found"));
                                    }
                                    organization.setLocation(location);
                                    return organizationRepository.persist(organization);
                                });
                    }

                    return organizationRepository.persist(organization);
                });
    }

    /**
     * Get organizations by type
     */
    public Uni<List<Organization>> getOrganizationsByType(OrganizationType organizationType) {
        return organizationRepository.findByOrganizationType(organizationType);
    }

    /**
     * Count total organizations
     */
    public Uni<Long> countOrganizations() {
        return organizationRepository.countTotalOrganizations();
    }
}

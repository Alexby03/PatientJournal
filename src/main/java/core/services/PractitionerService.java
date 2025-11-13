package core.services;

import data.entities.Practitioner;
import data.entities.Organization;
import data.repositories.PractitionerRepository;
import data.repositories.OrganizationRepository;
import api.dto.PractitionerCreateDTO;
import api.dto.PractitionerUpdateDTO;
import core.enums.UserType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PractitionerService {

    @Inject
    PractitionerRepository practitionerRepository;

    @Inject
    OrganizationRepository organizationRepository;

    /**
     * Get practitioner by ID
     */
    public Uni<Practitioner> getPractitionerById(UUID practitionerId) {
        return practitionerRepository.findById(practitionerId);
    }

    /**
     * Get practitioners by organization
     */
    public Uni<List<Practitioner>> getPractitionersByOrganization(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .chain(org -> {
                    if (org == null) {
                        return Uni.createFrom().failure(
                                new IllegalArgumentException("Organization not found"));
                    }
                    return practitionerRepository.findByOrganizationId(organizationId);
                });
    }

    /**
     * Get practitioner by email
     */
    public Uni<Practitioner> getPractitionerByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Email cannot be empty"));
        }
        return practitionerRepository.findByEmail(email);
    }

    /**
     * Create a new practitioner
     */
    public Uni<Practitioner> createPractitioner(PractitionerCreateDTO dto) {
        if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Full name is required"));
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Email is required"));
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Password is required"));
        }
        if (dto.getOrganizationId() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Organization ID is required"));
        }

        return organizationRepository.findById(dto.getOrganizationId())
                .chain(org -> {
                    if (org == null) {
                        return Uni.createFrom().failure(new IllegalArgumentException("Organization not found"));
                    }

                    return practitionerRepository.findByEmail(dto.getEmail())
                            .chain(existing -> {
                                if (existing != null) {
                                    return Uni.createFrom().failure(
                                            new IllegalArgumentException("Email already exists"));
                                }

                                Practitioner practitioner = new Practitioner(
                                        dto.getFullName(),
                                        dto.getEmail(),
                                        hashPassword(dto.getPassword()),
                                        UserType.Doctor,
                                        org
                                );

                                return practitionerRepository.persist(practitioner);
                            });
                });
    }

    /**
     * Update practitioner
     */
    public Uni<Practitioner> updatePractitioner(UUID practitionerId, PractitionerUpdateDTO dto) {
        return practitionerRepository.findById(practitionerId)
                .chain(practitioner -> {
                    if (practitioner == null) {
                        return Uni.createFrom().failure(new IllegalArgumentException("Practitioner not found"));
                    }

                    if (dto.getFullName() != null && !dto.getFullName().isEmpty()) {
                        practitioner.setFullName(dto.getFullName());
                    }
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        practitioner.setPassword(hashPassword(dto.getPassword()));
                    }

                    return practitionerRepository.persist(practitioner);
                });
    }

    /**
     * Delete a practitioner
     */
    public Uni<Boolean> deletePractitioner(UUID practitionerId) {
        return practitionerRepository.deleteById(practitionerId);
    }

    /**
     * Count practitioners in an organization
     */
    public Uni<Long> countPractitionersByOrganization(UUID organizationId) {
        return practitionerRepository.countByOrganization(organizationId);
    }

    private String hashPassword(String password) {
        return password; // TODO: implement proper hashing
    }
}

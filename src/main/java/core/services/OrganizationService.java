package core.services;

import api.dto.OrganizationCreateDTO;
import api.dto.OrganizationDTO;
import api.dto.OrganizationUpdateDTO;
import core.mappers.DTOMapper;
import data.entities.Location;
import data.entities.Organization;
import data.repositories.LocationRepository;
import data.repositories.OrganizationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrganizationService {

    @Inject
    OrganizationRepository organizationRepository;

    @Inject
    LocationRepository locationRepository;

    public List<OrganizationDTO> getAllOrganizations(int pageIndex, int pageSize) {
        List<Organization> organizations = organizationRepository.findAllOrganizations(pageIndex, pageSize);
        return organizations.stream()
                .map(DTOMapper::toOrganizationDTO)
                .collect(Collectors.toList());
    }

    public OrganizationDTO getOrganizationById(UUID organizationId) {
        Organization organization = organizationRepository.findById(organizationId);
        if (organization == null) {
            throw new IllegalArgumentException("Organization not found");
        }
        return DTOMapper.toOrganizationDTO(organization);
    }

    public List<OrganizationDTO> getOrganizationsByType(core.enums.OrganizationType organizationType) {
        List<Organization> organizations = organizationRepository.findByOrganizationType(organizationType);
        return organizations.stream()
                .map(DTOMapper::toOrganizationDTO)
                .collect(Collectors.toList());
    }

    public long countOrganizations() {
        return organizationRepository.countTotalOrganizations();
    }


    @Transactional
    public OrganizationDTO createOrganization(OrganizationCreateDTO dto) {
        if (dto.organizationType == null) {
            throw new IllegalArgumentException("Organization type is required");
        }
        if (dto.locationId == null) {
            throw new IllegalArgumentException("Location ID is required");
        }

        Location location = locationRepository.findById(dto.locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location not found");
        }

        Organization organization = new Organization(dto.organizationType, location);
        organizationRepository.persist(organization);

        return DTOMapper.toOrganizationDTO(organization);
    }


    @Transactional
    public OrganizationDTO updateOrganization(UUID organizationId, OrganizationUpdateDTO dto) {
        Organization organization = organizationRepository.findById(organizationId);
        if (organization == null) {
            throw new IllegalArgumentException("Organization not found");
        }

        if (dto.organizationType != null) {
            organization.setOrganizationType(dto.organizationType);
        }

        if (dto.locationId != null) {
            Location location = locationRepository.findById(dto.locationId);
            if (location == null) {
                throw new IllegalArgumentException("Location not found");
            }
            organization.setLocation(location);
        }

        organizationRepository.persist(organization);
        return DTOMapper.toOrganizationDTO(organization);
    }
}
package core.entities;

import core.enums.OrganizationType;

import java.util.UUID;

public record Organization(UUID organizationId, OrganizationType organizationType, Location location) {};

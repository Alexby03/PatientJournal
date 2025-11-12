package api.resources;

import data.entities.Organization;
import core.services.OrganizationService;
import api.dto.OrganizationCreateDTO;
import api.dto.OrganizationUpdateDTO;
import core.enums.OrganizationType;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    @Inject
    OrganizationService organizationService;

    /**
     * GET /api/organizations
     */
    @GET
    public Uni<Response> getAllOrganizations(
            @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return organizationService.getAllOrganizations(pageIndex, pageSize)
                .map(organizations -> Response.ok(organizations).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/organizations/{organizationId}
     */
    @GET
    @Path("/{organizationId}")
    public Uni<Response> getOrganizationById(@PathParam("organizationId") String organizationId) {
        return organizationService.getOrganizationById(java.util.UUID.fromString(organizationId))
                .map(organization -> {
                    if (organization == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(organization).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/organizations - Create organization with DTO
     */
    @POST
    public Uni<Response> createOrganization(OrganizationCreateDTO dto) {
        return organizationService.createOrganization(dto)
                .map(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * PUT /api/organizations/{organizationId} - Update organization with DTO
     */
    @PUT
    @Path("/{organizationId}")
    public Uni<Response> updateOrganization(@PathParam("organizationId") String organizationId,
                                            OrganizationUpdateDTO dto) {
        return organizationService.updateOrganization(java.util.UUID.fromString(organizationId), dto)
                .map(organization -> Response.ok(organization).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/organizations/type/{type}
     */
    @GET
    @Path("/type/{type}")
    public Uni<Response> getOrganizationsByType(@PathParam("type") String type) {
        return organizationService.getOrganizationsByType(OrganizationType.valueOf(type))
                .map(organizations -> Response.ok(organizations).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }
}

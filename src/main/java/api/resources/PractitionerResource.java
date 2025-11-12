package api.resources;

import data.entities.Practitioner;
import core.services.PractitionerService;
import api.dto.PractitionerCreateDTO;
import api.dto.PractitionerUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/practitioners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PractitionerResource {

    @Inject
    PractitionerService practitionerService;

    /**
     * GET /api/practitioners
     */
    @GET
    public Uni<Response> getAllPractitioners(
            @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return practitionerService.getAllPractitioners(pageIndex, pageSize)
                .map(practitioners -> Response.ok(practitioners).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/practitioners/{practitionerId}
     */
    @GET
    @Path("/{practitionerId}")
    public Uni<Response> getPractitionerById(@PathParam("practitionerId") String practitionerId) {
        return practitionerService.getPractitionerById(java.util.UUID.fromString(practitionerId))
                .map(practitioner -> {
                    if (practitioner == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(practitioner).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/practitioners/organization/{organizationId}
     */
    @GET
    @Path("/organization/{organizationId}")
    public Uni<Response> getPractitionersByOrganization(@PathParam("organizationId") String organizationId) {
        return practitionerService.getPractitionersByOrganization(java.util.UUID.fromString(organizationId))
                .map(practitioners -> Response.ok(practitioners).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/practitioners/email/{email}
     */
    @GET
    @Path("/email/{email}")
    public Uni<Response> getPractitionerByEmail(@PathParam("email") String email) {
        return practitionerService.getPractitionerByEmail(email)
                .map(practitioner -> {
                    if (practitioner == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(practitioner).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/practitioners - Create practitioner with DTO
     */
    @POST
    public Uni<Response> createPractitioner(PractitionerCreateDTO dto) {
        return practitionerService.createPractitioner(dto)
                .map(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * PUT /api/practitioners/{practitionerId} - Update practitioner with DTO
     */
    @PUT
    @Path("/{practitionerId}")
    public Uni<Response> updatePractitioner(@PathParam("practitionerId") String practitionerId,
                                            PractitionerUpdateDTO dto) {
        return practitionerService.updatePractitioner(java.util.UUID.fromString(practitionerId), dto)
                .map(practitioner -> Response.ok(practitioner).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/practitioners/{practitionerId}
     */
    @DELETE
    @Path("/{practitionerId}")
    public Uni<Response> deletePractitioner(@PathParam("practitionerId") String practitionerId) {
        return practitionerService.deletePractitioner(java.util.UUID.fromString(practitionerId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

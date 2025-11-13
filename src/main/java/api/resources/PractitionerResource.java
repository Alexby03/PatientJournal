package api.resources;

import core.mappers.PractitionerMapper;
import core.services.PractitionerService;
import api.dto.PractitionerCreateDTO;
import api.dto.PractitionerUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/practitioners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PractitionerResource {

    @Inject
    PractitionerService practitionerService;

    @GET
    @Path("/{practitionerId}")
    public Uni<Response> getPractitionerById(@PathParam("practitionerId") String practitionerId) {
        return practitionerService.getPractitionerById(UUID.fromString(practitionerId))
                .map(practitioner -> {
                    if (practitioner == null)
                        return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(PractitionerMapper.toDTO(practitioner)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/organization/{organizationId}")
    public Uni<Response> getPractitionersByOrganization(@PathParam("organizationId") String organizationId) {
        return practitionerService.getPractitionersByOrganization(UUID.fromString(organizationId))
                .map(practitioners -> Response.ok(
                        practitioners.stream()
                                .map(PractitionerMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/email/{email}")
    public Uni<Response> getPractitionerByEmail(@PathParam("email") String email) {
        return practitionerService.getPractitionerByEmail(email)
                .map(practitioner -> {
                    if (practitioner == null)
                        return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(PractitionerMapper.toDTO(practitioner)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @POST
    public Uni<Response> createPractitioner(PractitionerCreateDTO dto) {
        return practitionerService.createPractitioner(dto)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(PractitionerMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @PUT
    @Path("/{practitionerId}")
    public Uni<Response> updatePractitioner(@PathParam("practitionerId") String practitionerId,
                                            PractitionerUpdateDTO dto) {
        return practitionerService.updatePractitioner(UUID.fromString(practitionerId), dto)
                .map(updated -> Response.ok(PractitionerMapper.toDTO(updated)).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @DELETE
    @Path("/{practitionerId}")
    public Uni<Response> deletePractitioner(@PathParam("practitionerId") String practitionerId) {
        return practitionerService.deletePractitioner(UUID.fromString(practitionerId))
                .map(deleted -> {
                    if (deleted) return Response.noContent().build();
                    return Response.status(Response.Status.NOT_FOUND).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }
}

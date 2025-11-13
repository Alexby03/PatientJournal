package api.resources;

import core.mappers.EncounterMapper;
import core.services.EncounterService;
import api.dto.EncounterCreateDTO;
import api.dto.EncounterUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/encounters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EncounterResource {

    @Inject
    EncounterService encounterService;

    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientEncounters(@PathParam("patientId") String patientId) {
        return encounterService.getPatientEncounters(UUID.fromString(patientId))
                .map(encounters -> Response.ok(
                        encounters.stream()
                                .map(EncounterMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/{encounterId}")
    public Uni<Response> getEncounterById(@PathParam("encounterId") String encounterId) {
        return encounterService.getEncounterById(UUID.fromString(encounterId))
                .map(encounter -> {
                    if (encounter == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(EncounterMapper.toDTO(encounter)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createEncounter(@PathParam("patientId") String patientId,
                                         @PathParam("practitionerId") String practitionerId,
                                         EncounterCreateDTO dto) {
        return encounterService.createEncounter(UUID.fromString(patientId), UUID.fromString(practitionerId), dto)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(EncounterMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @PUT
    @Path("/{encounterId}")
    public Uni<Response> updateEncounter(@PathParam("encounterId") String encounterId,
                                         EncounterUpdateDTO dto) {
        return encounterService.updateEncounter(UUID.fromString(encounterId), dto)
                .map(updated -> Response.ok(EncounterMapper.toDTO(updated)).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @DELETE
    @Path("/{encounterId}")
    public Uni<Response> deleteEncounter(@PathParam("encounterId") String encounterId) {
        return encounterService.deleteEncounter(UUID.fromString(encounterId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/recent")
    public Uni<Response> getRecentEncounters() {
        return encounterService.getRecentEncounters()
                .map(encounters -> Response.ok(
                        encounters.stream()
                                .map(EncounterMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientEncounters(@PathParam("patientId") String patientId) {
        return encounterService.countPatientEncounters(UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

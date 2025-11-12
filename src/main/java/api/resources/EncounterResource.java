package api.resources;

import data.entities.Encounter;
import core.services.EncounterService;
import api.dto.EncounterCreateDTO;
import api.dto.EncounterUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/encounters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EncounterResource {

    @Inject
    EncounterService encounterService;

    /**
     * GET /api/encounters/patient/{patientId}
     */
    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientEncounters(@PathParam("patientId") String patientId) {
        return encounterService.getPatientEncounters(java.util.UUID.fromString(patientId))
                .map(encounters -> Response.ok(encounters).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/encounters/{encounterId}
     */
    @GET
    @Path("/{encounterId}")
    public Uni<Response> getEncounterById(@PathParam("encounterId") String encounterId) {
        return encounterService.getEncounterById(java.util.UUID.fromString(encounterId))
                .map(encounter -> {
                    if (encounter == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(encounter).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/encounters/patient/{patientId}/practitioner/{practitionerId} - Create encounter with DTO
     */
    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createEncounter(@PathParam("patientId") String patientId,
                                         @PathParam("practitionerId") String practitionerId,
                                         EncounterCreateDTO dto) {
        return encounterService.createEncounter(
                        java.util.UUID.fromString(patientId),
                        java.util.UUID.fromString(practitionerId),
                        dto)
                .map(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * PUT /api/encounters/{encounterId} - Update encounter with DTO
     */
    @PUT
    @Path("/{encounterId}")
    public Uni<Response> updateEncounter(@PathParam("encounterId") String encounterId,
                                         EncounterUpdateDTO dto) {
        return encounterService.updateEncounter(java.util.UUID.fromString(encounterId), dto)
                .map(encounter -> Response.ok(encounter).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/encounters/{encounterId}
     */
    @DELETE
    @Path("/{encounterId}")
    public Uni<Response> deleteEncounter(@PathParam("encounterId") String encounterId) {
        return encounterService.deleteEncounter(java.util.UUID.fromString(encounterId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/encounters/recent
     */
    @GET
    @Path("/recent")
    public Uni<Response> getRecentEncounters() {
        return encounterService.getRecentEncounters()
                .map(encounters -> Response.ok(encounters).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/encounters/count/patient/{patientId}
     */
    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientEncounters(@PathParam("patientId") String patientId) {
        return encounterService.countPatientEncounters(java.util.UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

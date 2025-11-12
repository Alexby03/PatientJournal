package api.resources;

import data.entities.Observation;
import core.services.ObservationService;
import api.dto.ObservationCreateDTO;
import api.dto.ObservationUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/observations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObservationResource {

    @Inject
    ObservationService observationService;

    /**
     * GET /api/observations/patient/{patientId}
     */
    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientObservations(@PathParam("patientId") String patientId) {
        return observationService.getPatientObservations(java.util.UUID.fromString(patientId))
                .map(observations -> Response.ok(observations).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/observations/{observationId}
     */
    @GET
    @Path("/{observationId}")
    public Uni<Response> getObservationById(@PathParam("observationId") String observationId) {
        return observationService.getObservationById(java.util.UUID.fromString(observationId))
                .map(observation -> {
                    if (observation == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(observation).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/observations/patient/{patientId}/practitioner/{practitionerId} - Create observation with DTO
     */
    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createObservation(@PathParam("patientId") String patientId,
                                           @PathParam("practitionerId") String practitionerId,
                                           ObservationCreateDTO dto) {
        return observationService.createObservation(
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
     * PUT /api/observations/{observationId} - Update observation with DTO
     */
    @PUT
    @Path("/{observationId}")
    public Uni<Response> updateObservation(@PathParam("observationId") String observationId,
                                           ObservationUpdateDTO dto) {
        return observationService.updateObservation(java.util.UUID.fromString(observationId), dto)
                .map(observation -> Response.ok(observation).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/observations/{observationId}
     */
    @DELETE
    @Path("/{observationId}")
    public Uni<Response> deleteObservation(@PathParam("observationId") String observationId) {
        return observationService.deleteObservation(java.util.UUID.fromString(observationId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/observations/recent/patient/{patientId}
     */
    @GET
    @Path("/recent/patient/{patientId}")
    public Uni<Response> getMostRecentObservation(@PathParam("patientId") String patientId) {
        return observationService.getMostRecentObservation(java.util.UUID.fromString(patientId))
                .map(observation -> {
                    if (observation == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(observation).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/observations/count/patient/{patientId}
     */
    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientObservations(@PathParam("patientId") String patientId) {
        return observationService.countPatientObservations(java.util.UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

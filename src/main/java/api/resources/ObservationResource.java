package api.resources;

import core.mappers.ObservationMapper;
import core.services.ObservationService;
import api.dto.ObservationCreateDTO;
import api.dto.ObservationUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/observations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObservationResource {

    @Inject
    ObservationService observationService;

    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientObservations(@PathParam("patientId") String patientId) {
        return observationService.getPatientObservations(UUID.fromString(patientId))
                .map(observations -> Response.ok(
                        observations.stream()
                                .map(ObservationMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    @GET
    @Path("/{observationId}")
    public Uni<Response> getObservationById(@PathParam("observationId") String observationId) {
        return observationService.getObservationById(UUID.fromString(observationId))
                .map(observation -> {
                    if (observation == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(ObservationMapper.toDTO(observation)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createObservation(@PathParam("patientId") String patientId,
                                           @PathParam("practitionerId") String practitionerId,
                                           ObservationCreateDTO dto) {
        return observationService.createObservation(
                        UUID.fromString(patientId),
                        UUID.fromString(practitionerId),
                        dto)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(ObservationMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    @PUT
    @Path("/{observationId}")
    public Uni<Response> updateObservation(@PathParam("observationId") String observationId,
                                           ObservationUpdateDTO dto) {
        return observationService.updateObservation(UUID.fromString(observationId), dto)
                .map(updated -> Response.ok(ObservationMapper.toDTO(updated)).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    @DELETE
    @Path("/{observationId}")
    public Uni<Response> deleteObservation(@PathParam("observationId") String observationId) {
        return observationService.deleteObservation(UUID.fromString(observationId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/recent/patient/{patientId}")
    public Uni<Response> getMostRecentObservation(@PathParam("patientId") String patientId) {
        return observationService.getMostRecentObservation(UUID.fromString(patientId))
                .map(observation -> {
                    if (observation == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(ObservationMapper.toDTO(observation)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientObservations(@PathParam("patientId") String patientId) {
        return observationService.countPatientObservations(UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

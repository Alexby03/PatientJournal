package api.resources;

import core.mappers.ConditionMapper;
import core.services.ConditionService;
import api.dto.ConditionCreateDTO;
import api.dto.ConditionUpdateDTO;
import core.enums.ConditionType;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/conditions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConditionResource {

    @Inject
    ConditionService conditionService;

    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientConditions(@PathParam("patientId") String patientId) {
        return conditionService.getPatientConditions(UUID.fromString(patientId))
                .map(conditions -> Response.ok(
                        conditions.stream()
                                .map(ConditionMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/{conditionId}")
    public Uni<Response> getConditionById(@PathParam("conditionId") String conditionId) {
        return conditionService.getConditionById(UUID.fromString(conditionId))
                .map(condition -> {
                    if (condition == null)
                        return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(ConditionMapper.toDTO(condition)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createCondition(@PathParam("patientId") String patientId,
                                         @PathParam("practitionerId") String practitionerId,
                                         ConditionCreateDTO dto) {
        return conditionService.createCondition(UUID.fromString(patientId), UUID.fromString(practitionerId), dto)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(ConditionMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @PUT
    @Path("/{conditionId}")
    public Uni<Response> updateCondition(@PathParam("conditionId") String conditionId,
                                         ConditionUpdateDTO dto) {
        return conditionService.updateCondition(UUID.fromString(conditionId), dto)
                .map(updated -> Response.ok(ConditionMapper.toDTO(updated)).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @DELETE
    @Path("/{conditionId}")
    public Uni<Response> deleteCondition(@PathParam("conditionId") String conditionId) {
        return conditionService.deleteCondition(UUID.fromString(conditionId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/high-severity")
    public Uni<Response> getHighSeverityConditions() {
        return conditionService.getHighSeverityConditions()
                .map(conditions -> Response.ok(
                        conditions.stream()
                                .map(ConditionMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/type/{conditionType}")
    public Uni<Response> getConditionsByType(@PathParam("conditionType") String conditionType) {
        return conditionService.getConditionsByType(ConditionType.valueOf(conditionType))
                .map(conditions -> Response.ok(
                        conditions.stream()
                                .map(ConditionMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientConditions(@PathParam("patientId") String patientId) {
        return conditionService.countPatientConditions(UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }
}

package api.resources;

import data.entities.Condition;
import core.services.ConditionService;
import api.dto.ConditionCreateDTO;
import api.dto.ConditionUpdateDTO;
import core.enums.ConditionType;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/conditions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConditionResource {

    @Inject
    ConditionService conditionService;

    /**
     * GET /api/conditions/patient/{patientId}
     */
    @GET
    @Path("/patient/{patientId}")
    public Uni<Response> getPatientConditions(@PathParam("patientId") String patientId) {
        return conditionService.getPatientConditions(java.util.UUID.fromString(patientId))
                .map(conditions -> Response.ok(conditions).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/conditions/{conditionId}
     */
    @GET
    @Path("/{conditionId}")
    public Uni<Response> getConditionById(@PathParam("conditionId") String conditionId) {
        return conditionService.getConditionById(java.util.UUID.fromString(conditionId))
                .map(condition -> {
                    if (condition == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(condition).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/conditions/patient/{patientId}/practitioner/{practitionerId} - Create condition with DTO
     */
    @POST
    @Path("/patient/{patientId}/practitioner/{practitionerId}")
    public Uni<Response> createCondition(@PathParam("patientId") String patientId,
                                         @PathParam("practitionerId") String practitionerId,
                                         ConditionCreateDTO dto) {
        return conditionService.createCondition(
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
     * PUT /api/conditions/{conditionId} - Update condition with DTO
     */
    @PUT
    @Path("/{conditionId}")
    public Uni<Response> updateCondition(@PathParam("conditionId") String conditionId,
                                         ConditionUpdateDTO dto) {
        return conditionService.updateCondition(java.util.UUID.fromString(conditionId), dto)
                .map(condition -> Response.ok(condition).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/conditions/{conditionId}
     */
    @DELETE
    @Path("/{conditionId}")
    public Uni<Response> deleteCondition(@PathParam("conditionId") String conditionId) {
        return conditionService.deleteCondition(java.util.UUID.fromString(conditionId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/conditions/high-severity
     */
    @GET
    @Path("/high-severity")
    public Uni<Response> getHighSeverityConditions() {
        return conditionService.getHighSeverityConditions()
                .map(conditions -> Response.ok(conditions).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/conditions/type/{conditionType}
     */
    @GET
    @Path("/type/{conditionType}")
    public Uni<Response> getConditionsByType(@PathParam("conditionType") String conditionType) {
        return conditionService.getConditionsByType(ConditionType.valueOf(conditionType))
                .map(conditions -> Response.ok(conditions).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/conditions/count/patient/{patientId}
     */
    @GET
    @Path("/count/patient/{patientId}")
    public Uni<Response> countPatientConditions(@PathParam("patientId") String patientId) {
        return conditionService.countPatientConditions(java.util.UUID.fromString(patientId))
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

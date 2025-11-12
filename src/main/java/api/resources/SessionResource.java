package api.resources;

import data.entities.Session;
import core.services.SessionService;
import api.dto.SessionCreateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {

    @Inject
    SessionService sessionService;

    /**
     * GET /api/sessions/user/{userId}
     */
    @GET
    @Path("/user/{userId}")
    public Uni<Response> getUserSessions(@PathParam("userId") String userId) {
        return sessionService.getUserSessions(java.util.UUID.fromString(userId))
                .map(sessions -> Response.ok(sessions).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/sessions/{sessionId}
     */
    @GET
    @Path("/{sessionId}")
    public Uni<Response> getSessionById(@PathParam("sessionId") String sessionId) {
        return sessionService.getSessionById(java.util.UUID.fromString(sessionId))
                .map(session -> {
                    if (session == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(session).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/sessions - Create session with DTO
     */
    @POST
    public Uni<Response> createSession(SessionCreateDTO dto) {
        return sessionService.createSession(dto)
                .map(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/sessions/between
     */
    @GET
    @Path("/between")
    public Uni<Response> getSessionsBetweenUsers(@QueryParam("user1") String user1,
                                                 @QueryParam("user2") String user2) {
        return sessionService.getSessionsBetweenUsers(
                        java.util.UUID.fromString(user1),
                        java.util.UUID.fromString(user2))
                .map(sessions -> Response.ok(sessions).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/sessions/{sessionId}
     */
    @DELETE
    @Path("/{sessionId}")
    public Uni<Response> deleteSession(@PathParam("sessionId") String sessionId) {
        return sessionService.deleteSession(java.util.UUID.fromString(sessionId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

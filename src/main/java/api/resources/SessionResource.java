package api.resources;

import api.dto.SessionDTO;
import api.dto.SessionCreateDTO;
import core.mappers.SessionMapper;
import core.services.SessionService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return sessionService.getUserSessions(UUID.fromString(userId))
                .map(sessions -> {
                    List<SessionDTO> dtos = sessions.stream()
                            .map(SessionMapper::toDTO)
                            .collect(Collectors.toList());
                    return Response.ok(dtos).build();
                })
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
        return sessionService.getSessionById(UUID.fromString(sessionId))
                .map(session -> {
                    if (session == null) return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(SessionMapper.toDTO(session)).build();
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
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(SessionMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/sessions/between?user1=...&user2=...
     */
    @GET
    @Path("/between")
    public Uni<Response> getSessionsBetweenUsers(@QueryParam("user1") String user1,
                                                 @QueryParam("user2") String user2) {
        return sessionService.getSessionsBetweenUsers(UUID.fromString(user1), UUID.fromString(user2))
                .map(sessions -> {
                    List<SessionDTO> dtos = sessions.stream()
                            .map(SessionMapper::toDTO)
                            .collect(Collectors.toList());
                    return Response.ok(dtos).build();
                })
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
        return sessionService.deleteSession(UUID.fromString(sessionId))
                .map(deleted -> deleted
                        ? Response.noContent().build()
                        : Response.status(Response.Status.NOT_FOUND).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

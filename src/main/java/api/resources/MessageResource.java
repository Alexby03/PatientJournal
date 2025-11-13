package api.resources;

import api.dto.MessageDTO;
import api.dto.MessageCreateDTO;
import core.mappers.MessageMapper;
import core.services.MessageService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    MessageService messageService;

    /**
     * GET /api/messages/session/{sessionId}
     */
    @GET
    @Path("/session/{sessionId}")
    public Uni<Response> getSessionMessages(@PathParam("sessionId") String sessionId) {
        return messageService.getSessionMessages(java.util.UUID.fromString(sessionId))
                .map(messages -> messages.stream()
                        .map(MessageMapper::toDTO)
                        .collect(Collectors.toList()))
                .map(dtos -> Response.ok(dtos).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/messages/{messageId}
     */
    @GET
    @Path("/{messageId}")
    public Uni<Response> getMessageById(@PathParam("messageId") String messageId) {
        return messageService.getMessageById(java.util.UUID.fromString(messageId))
                .map(MessageMapper::toDTO)
                .map(dto -> {
                    if (dto == null) return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(dto).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/messages - Create message with DTO
     */
    @POST
    public Uni<Response> createMessage(MessageCreateDTO dto) {
        return messageService.createMessage(dto)
                .map(MessageMapper::toDTO)
                .map(createdDto -> Response.status(Response.Status.CREATED).entity(createdDto).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/messages/{messageId}
     */
    @DELETE
    @Path("/{messageId}")
    public Uni<Response> deleteMessage(@PathParam("messageId") String messageId) {
        return messageService.deleteMessage(java.util.UUID.fromString(messageId))
                .map(deleted -> deleted
                        ? Response.noContent().build()
                        : Response.status(Response.Status.NOT_FOUND).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/messages/latest/session/{sessionId}
     */
    @GET
    @Path("/latest/session/{sessionId}")
    public Uni<Response> getLatestMessage(@PathParam("sessionId") String sessionId) {
        return messageService.getLatestMessage(java.util.UUID.fromString(sessionId))
                .map(MessageMapper::toDTO)
                .map(dto -> {
                    if (dto == null) return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(dto).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}
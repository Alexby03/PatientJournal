package api.resources;

import data.entities.User;
import core.services.UserService;
import api.dto.UserCreateDTO;
import api.dto.UserUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    /**
     * GET /api/users - Get all users with pagination
     */
    @GET
    public Uni<Response> getAllUsers(
            @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return userService.getAllUsers(pageIndex, pageSize)
                .map(users -> Response.ok(users).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("Error fetching users")
                                .build());
    }

    /**
     * GET /api/users/{userId}
     */
    @GET
    @Path("/{userId}")
    public Uni<Response> getUserById(@PathParam("userId") String userId) {
        return userService.getUserById(java.util.UUID.fromString(userId))
                .map(user -> {
                    if (user == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(user).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/users/email/{email}
     */
    @GET
    @Path("/email/{email}")
    public Uni<Response> getUserByEmail(@PathParam("email") String email) {
        return userService.getUserByEmail(email)
                .map(user -> {
                    if (user == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(user).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * DELETE /api/users/{userId}
     */
    @DELETE
    @Path("/{userId}")
    public Uni<Response> deleteUser(@PathParam("userId") String userId) {
        return userService.deleteUser(java.util.UUID.fromString(userId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/users/count
     */
    @GET
    @Path("/count")
    public Uni<Response> countUsers() {
        return userService.countUsers()
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

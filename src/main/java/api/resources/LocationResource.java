package api.resources;

import data.entities.Location;
import core.services.LocationService;
import api.dto.LocationCreateDTO;
import api.dto.LocationUpdateDTO;
import core.enums.LocationType;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    LocationService locationService;

    /**
     * GET /api/locations
     */
    @GET
    public Uni<Response> getAllLocations() {
        return locationService.getAllLocations()
                .map(locations -> Response.ok(locations).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/locations/{locationId}
     */
    @GET
    @Path("/{locationId}")
    public Uni<Response> getLocationById(@PathParam("locationId") String locationId) {
        return locationService.getLocationById(java.util.UUID.fromString(locationId))
                .map(location -> {
                    if (location == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(location).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/locations - Create location with DTO
     */
    @POST
    public Uni<Response> createLocation(LocationCreateDTO dto) {
        return locationService.createLocation(dto)
                .map(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * PUT /api/locations/{locationId} - Update location with DTO
     */
    @PUT
    @Path("/{locationId}")
    public Uni<Response> updateLocation(@PathParam("locationId") String locationId,
                                        LocationUpdateDTO dto) {
        return locationService.updateLocation(java.util.UUID.fromString(locationId), dto)
                .map(location -> Response.ok(location).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/locations/type/{type}
     */
    @GET
    @Path("/type/{type}")
    public Uni<Response> getLocationsByType(@PathParam("type") String type) {
        return locationService.getLocationsByType(LocationType.valueOf(type))
                .map(locations -> Response.ok(locations).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }
}

package api.resources;

import api.dto.LocationCreateDTO;
import api.dto.LocationDTO;
import api.dto.LocationUpdateDTO;
import core.enums.LocationType;
import core.services.LocationService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    LocationService locationService;

    // =======================
    // GET
    // =======================

    /** Get all locations */
    @GET
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }

    /** Get location by ID */
    @GET
    @Path("/{locationId}")
    public LocationDTO getLocationById(@PathParam("locationId") UUID locationId) {
        return locationService.getLocationById(locationId);
    }

    /** Get locations by type */
    @GET
    @Path("/type/{type}")
    public List<LocationDTO> getLocationsByType(@PathParam("type") LocationType type) {
        return locationService.getLocationsByType(type);
    }

    /** Count total locations */
    @GET
    @Path("/count")
    public long countLocations() {
        return locationService.countLocations();
    }

    // =======================
    // POST
    // =======================

    /** Create a new location */
    @POST
    @Transactional
    public LocationDTO createLocation(LocationCreateDTO dto) {
        return locationService.createLocation(dto);
    }

    // =======================
    // PUT
    // =======================

    /** Update existing location */
    @PUT
    @Path("/{locationId}")
    @Transactional
    public LocationDTO updateLocation(@PathParam("locationId") UUID locationId,
                                      LocationUpdateDTO dto) {
        return locationService.updateLocation(locationId, dto);
    }

    // =======================
    // DELETE
    // =======================

    /** Delete location */
    @DELETE
    @Path("/{locationId}")
    @Transactional
    public Response deleteLocation(@PathParam("locationId") UUID locationId) {
        boolean deleted = locationService.deleteLocation(locationId);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Location not found")
                    .build();
        }
    }
}

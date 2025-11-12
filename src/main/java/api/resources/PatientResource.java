package api.resources;

import data.entities.Patient;
import core.services.PatientService;
import api.dto.PatientCreateDTO;
import api.dto.PatientUpdateDTO;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService patientService;

    /**
     * GET /api/patients
     */
    @GET
    public Uni<Response> getAllPatients(
            @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return patientService.getAllPatients(pageIndex, pageSize)
                .map(patients -> Response.ok(patients).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/patients/{patientId}
     */
    @GET
    @Path("/{patientId}")
    public Uni<Response> getPatientById(@PathParam("patientId") String patientId) {
        return patientService.getPatientById(java.util.UUID.fromString(patientId))
                .map(patient -> {
                    if (patient == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(patient).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/patients/email/{email}
     */
    @GET
    @Path("/email/{email}")
    public Uni<Response> getPatientByEmail(@PathParam("email") String email) {
        return patientService.getPatientByEmail(email)
                .map(patient -> {
                    if (patient == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(patient).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * POST /api/patients - Create patient with DTO
     */
    @POST
    public Uni<Response> createPatient(PatientCreateDTO dto) {
        return patientService.createPatient(dto)
                .map(createdPatient -> Response.status(Response.Status.CREATED).entity(createdPatient).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * PUT /api/patients/{patientId} - Update patient with DTO
     */
    @PUT
    @Path("/{patientId}")
    public Uni<Response> updatePatient(@PathParam("patientId") String patientId, PatientUpdateDTO dto) {
        return patientService.updatePatient(java.util.UUID.fromString(patientId), dto)
                .map(patient -> Response.ok(patient).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * DELETE /api/patients/{patientId}
     */
    @DELETE
    @Path("/{patientId}")
    public Uni<Response> deletePatient(@PathParam("patientId") String patientId) {
        return patientService.deletePatient(java.util.UUID.fromString(patientId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * GET /api/patients/search
     */
    @GET
    @Path("/search")
    public Uni<Response> searchPatients(@QueryParam("q") String searchTerm) {
        return patientService.searchPatients(searchTerm)
                .map(patients -> Response.ok(patients).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage())
                                .build());
    }

    /**
     * GET /api/patients/count
     */
    @GET
    @Path("/count")
    public Uni<Response> countPatients() {
        return patientService.countPatients()
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}

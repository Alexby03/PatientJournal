package api.resources;

import data.entities.Patient;
import core.services.PatientService;
import api.dto.PatientCreateDTO;
import api.dto.PatientUpdateDTO;
import api.dto.PatientDTO;
import core.mappers.PatientMapper;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService patientService;

    @GET
    public Uni<Response> getAllPatients(
            @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return patientService.getAllPatients(pageIndex, pageSize)
                .map(patients -> Response.ok(
                        patients.stream()
                                .map(PatientMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/{patientId}")
    public Uni<Response> getPatientById(@PathParam("patientId") String patientId,
                                        @QueryParam("fetchRelations") @DefaultValue("true") boolean fetchRelations) {
        return patientService.getPatientById(UUID.fromString(patientId), fetchRelations)
                .map(patient -> {
                    if (patient == null) return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(PatientMapper.toDTO(patient)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/email/{email}")
    public Uni<Response> getPatientByEmail(@PathParam("email") String email) {
        return patientService.getPatientByEmail(email)
                .map(patient -> {
                    if (patient == null) return Response.status(Response.Status.NOT_FOUND).build();
                    return Response.ok(PatientMapper.toDTO(patient)).build();
                })
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @POST
    public Uni<Response> createPatient(PatientCreateDTO dto) {
        return patientService.createPatient(dto)
                .map(created -> Response.status(Response.Status.CREATED)
                        .entity(PatientMapper.toDTO(created))
                        .build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @PUT
    @Path("/{patientId}")
    public Uni<Response> updatePatient(@PathParam("patientId") String patientId, PatientUpdateDTO dto) {
        return patientService.updatePatient(UUID.fromString(patientId), dto)
                .map(updated -> Response.ok(PatientMapper.toDTO(updated)).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @DELETE
    @Path("/{patientId}")
    public Uni<Response> deletePatient(@PathParam("patientId") String patientId) {
        return patientService.deletePatient(UUID.fromString(patientId))
                .map(deleted -> Response.ok().build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/search")
    public Uni<Response> searchPatients(@QueryParam("q") String searchTerm,
                                        @QueryParam("pageIndex") @DefaultValue("0") int pageIndex,
                                        @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return patientService.searchPatientsByName(searchTerm, pageIndex, pageSize)
                .map(list -> Response.ok(
                        list.stream()
                                .map(PatientMapper::toDTO)
                                .collect(Collectors.toList())
                ).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.BAD_REQUEST)
                                .entity(err.getMessage()).build());
    }

    @GET
    @Path("/count")
    public Uni<Response> countPatients() {
        return patientService.countPatients()
                .map(count -> Response.ok(count).build())
                .onFailure().recoverWithItem(err ->
                        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(err.getMessage()).build());
    }
}

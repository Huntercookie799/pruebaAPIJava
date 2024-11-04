package org.utl.dsm403.elZarape;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.dsm403.elZarape.controladores.CiudadesController;
import org.utl.dsm.dsm403.elZarape.controladores.EstadosController;
import org.utl.dsm.dsm403.elZarape.modelos.Ciudad;
import org.utl.dsm.dsm403.elZarape.modelos.Estado;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author angel
 */
@Path("/")
public class RESTbase extends Application {

    private final Gson gson = new Gson();

    @Path("estados")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllEstados() {
        EstadosController controller = new EstadosController();
        try {
            List<Estado> estados = controller.getAll();
            String json = gson.toJson(estados);
            return Response.ok(json).build();
        } catch (SQLException e) {
            return manejarError("Error al obtener los datos de los estados.", e);
        }
    }

    @Path("ciudades")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllCiudades() {
        CiudadesController controller = new CiudadesController();
        try {
            List<Ciudad> ciudades = controller.getAll();
            String json = gson.toJson(ciudades);
            return Response.ok(json).build();
        } catch (SQLException e) {
            return manejarError("Error al obtener los datos de todas las ciudades.", e);
        }
    }

    @Path("ciudades/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getCiudadesByEstado(@PathParam("idEstado") int idEstado) {
        CiudadesController controller = new CiudadesController();
        try {
            List<Ciudad> ciudades = controller.getCiudadesPorEstado(idEstado);
            if (ciudades.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("{\"mensaje\": \"No se encontraron ciudades para el estado especificado.\"}").build();
            }
            String json = gson.toJson(ciudades);
            return Response.ok(json).build();
        } catch (SQLException e) {
            return manejarError("Error al obtener los datos de las ciudades por estado", e);
        }
    }


    private Response manejarError(String mensaje, Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("{\"error\": \"" + mensaje + ": " + e.getMessage() + "\"}").build();
    }
}

package org.utl.dsm403.app_base;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm403.prueba.controlador.ControllerTblBase;
import org.utl.dsm403.prueba.model.Tblbase;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author angel
 */
@Path("prueba")
public class RESTbase extends Application {

    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAll() {
        String out = null;
        List<Tblbase> pruebas = null;
        ControllerTblBase cb = new ControllerTblBase();

        try {
            pruebas = cb.getAll();
            out = new Gson().toJson(pruebas);
        } catch (SQLException e) {
            out = """
                  {"error": "Error en la base de datos: Verifique la conexión o los datos"}
                  """;
        } catch (NullPointerException e) {
            out = """
                  {"error": "Error: Datos nulos encontrados, verifique los valores en la base de datos"}
                  """;
        } catch (Exception e) {
            out = """
                  {"error": "Ocurrió un error inesperado. Intente más tarde"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

}

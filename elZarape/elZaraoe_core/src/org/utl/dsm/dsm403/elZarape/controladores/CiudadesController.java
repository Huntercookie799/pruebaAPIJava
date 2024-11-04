package org.utl.dsm.dsm403.elZarape.controladores;

import org.utl.dsm.dsm403.elZarape.modelos.Ciudad;
import org.utl.dsm.dsm403.elZarape.modelos.Estado;
import org.utl.dsm.dsm403.elZarape.conexion.ConexionMySQL;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CiudadesController {

    private Ciudad fill(ResultSet rs) throws SQLException {
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(rs.getInt("idCiudad"));
        ciudad.setNombre(rs.getString("nombre"));

        Estado estado = new Estado();
        estado.setIdEstado(rs.getInt("idEstado"));
        estado.setNombre(rs.getString("nombre_estado"));
        ciudad.setEstado(estado);

        return ciudad;
    }

    public List<Ciudad> getAll() throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();

        String sql = "SELECT ciudad.*, estado.idEstado, estado.nombre AS nombre_estado FROM ciudad "
                   + "JOIN estado ON ciudad.idEstado = estado.idEstado;";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            ciudades.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return ciudades;
    }

    public List<Ciudad> getCiudadesPorEstado(int idEstado) throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();

        String sql = "SELECT ciudad.*, estado.idEstado, estado.nombre AS nombre_estado FROM ciudad "
                   + "JOIN estado ON ciudad.idEstado = estado.idEstado "
                   + "WHERE estado.idEstado = ?;";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setInt(1, idEstado);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            ciudades.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return ciudades;
    }
}

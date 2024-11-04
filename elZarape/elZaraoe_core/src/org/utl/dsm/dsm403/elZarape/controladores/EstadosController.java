package org.utl.dsm.dsm403.elZarape.controladores;

import org.utl.dsm.dsm403.elZarape.modelos.Estado;
import org.utl.dsm.dsm403.elZarape.conexion.ConexionMySQL;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EstadosController {

    private Estado fill(ResultSet rs) throws SQLException {
        Estado estado = new Estado();
        estado.setIdEstado(rs.getInt("idEstado"));
        estado.setNombre(rs.getString("nombre"));
        return estado;
    }

    public List<Estado> getAll() throws SQLException {
        List<Estado> estados = new ArrayList<>();

        String sql = "SELECT * FROM estado;";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            estados.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return estados;
    }
}

package org.utl.dsm403.prueba.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.utl.dsm403.prueba.db.ConexionMySQL;

public class ControladorMysql {
    private ConexionMySQL conexionMySQL;

    public ControladorMysql() {
        conexionMySQL = new ConexionMySQL();
    }

    public boolean probarConexion() {
        Connection conn = null;
        try {
            conn = conexionMySQL.open();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (conn != null) {
                try {
                    conexionMySQL.close();
                } catch (Exception ex) {
                    return false;
                }
            }
        }
    }

    private void cerrarRecursos(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conexionMySQL.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

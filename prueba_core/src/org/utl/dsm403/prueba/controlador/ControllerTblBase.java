package org.utl.dsm403.prueba.controlador;

import org.utl.dsm403.prueba.model.Tblbase;
import org.utl.dsm403.prueba.db.ConexionMySQL;

import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ControllerTblBase {
    
   private Tblbase fill(ResultSet rs) throws SQLException{
       Tblbase base = new Tblbase();
       base.setIdBase(rs.getInt("id_base"));
       base.setEdad(rs.getInt("edad"));
       base.setNombre(rs.getString("nombre"));
       return base;
   }

   public List<Tblbase> getAll() throws SQLException {
        List<Tblbase> pruebas = new ArrayList<Tblbase>();
        
        String sql = "SELECT * FROM v_base;";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            pruebas.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return pruebas;
    }

    
}

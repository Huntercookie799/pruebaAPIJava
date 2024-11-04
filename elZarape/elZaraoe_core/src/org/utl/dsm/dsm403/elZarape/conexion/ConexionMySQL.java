package org.utl.dsm.dsm403.elZarape.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionMySQL {

    Connection conn;

    public Connection open() {
        String user = "root";
        String password = "2005";

        String url = "jdbc:mysql://localhost:3306/zarape?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException e) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, "Connection failed", e);
            return null; // Return null instead of throwing exception
        } catch (ClassNotFoundException e) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, "JDBC Driver not found", e);
            return null;
        }

    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Excepción controlada");
        }
    }
}

package org.utl.dsm.dsm403.elZarape.controladores;

import org.utl.dsm.dsm403.elZarape.modelos.*;
import org.utl.dsm.dsm403.elZarape.conexion.ConexionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoController {
    
    private Empleado fill(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(rs.getInt("idEmpleado"));
        empleado.setActivo(rs.getInt("activo"));
        
        // Llenar datos de Persona
        Persona persona = new Persona();
        persona.setIdPersona(rs.getInt("idPersona"));
        persona.setNombre(rs.getString("nombre"));
        persona.setApellidos(rs.getString("apellidos"));
        persona.setTelefono(rs.getString("telefono"));
        
        // Llenar datos de Ciudad para Persona
        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(rs.getInt("idCiudad"));
        ciudad.setNombre(rs.getString("nombreCiudad"));
        persona.setCiudad(ciudad);
        
        // Llenar datos de Usuario
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombreUsuario"));
        usuario.setActivo(rs.getInt("activoUsuario"));
        
        // Llenar datos de Sucursal
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(rs.getInt("idSucursal"));
        sucursal.setNombre(rs.getString("nombreSucursal"));
        
        empleado.setPersona(persona);
        empleado.setUsuario(usuario);
        empleado.setSucursal(sucursal);
        
        return empleado;
    }
    
    public int insert(Empleado empleado) throws SQLException {
        String sql = "{CALL insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(sql);
        
        // Establecer parámetros de entrada
        cstmt.setString(1, empleado.getPersona().getNombre());
        cstmt.setString(2, empleado.getPersona().getApellidos());
        cstmt.setString(3, empleado.getPersona().getTelefono());
        cstmt.setInt(4, empleado.getPersona().getCiudad().getIdCiudad());
        cstmt.setString(5, empleado.getUsuario().getNombre());
        cstmt.setString(6, empleado.getUsuario().getContrasenia());
        cstmt.setInt(7, empleado.getSucursal().getIdSucursal());
        
        // Registrar parámetros de salida
        cstmt.registerOutParameter(8, Types.INTEGER);
        cstmt.registerOutParameter(9, Types.INTEGER);
        cstmt.registerOutParameter(10, Types.INTEGER);
        
        // Ejecutar el procedimiento almacenado
        cstmt.executeUpdate();
        
        // Obtener los IDs generados
        empleado.getPersona().setIdPersona(cstmt.getInt(8));
        empleado.getUsuario().setIdUsuario(cstmt.getInt(9));
        empleado.setIdEmpleado(cstmt.getInt(10));
        
        cstmt.close();
        conn.close();
        
        return empleado.getIdEmpleado();
    }
    
    public List<Empleado> getAll() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        
        String sql = "SELECT e.idEmpleado, e.activo, "
                   + "p.idPersona, p.nombre, p.apellidos, p.telefono, "
                   + "c.idCiudad, c.nombre as nombreCiudad, "
                   + "u.idUsuario, u.nombre as nombreUsuario, u.activo as activoUsuario, "
                   + "s.idSucursal, s.nombre as nombreSucursal "
                   + "FROM empleado e "
                   + "INNER JOIN persona p ON e.idPersona = p.idPersona "
                   + "INNER JOIN ciudad c ON p.idCiudad = c.idCiudad "
                   + "INNER JOIN usuario u ON e.idUsuario = u.idUsuario "
                   + "INNER JOIN sucursal s ON e.idSucursal = s.idSucursal "
                   + "WHERE e.activo = 1;";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            empleados.add(fill(rs));
        }
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return empleados;
    }
} 
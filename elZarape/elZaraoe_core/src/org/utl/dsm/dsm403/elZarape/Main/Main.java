package org.utl.dsm.dsm403.elZarape.Main;

import org.utl.dsm.dsm403.elZarape.controladores.CiudadesController;
import org.utl.dsm.dsm403.elZarape.controladores.EstadosController;
import org.utl.dsm.dsm403.elZarape.controladores.EmpleadoController;
import org.utl.dsm.dsm403.elZarape.modelos.Ciudad;
import org.utl.dsm.dsm403.elZarape.modelos.Empleado;
import org.utl.dsm.dsm403.elZarape.modelos.Estado;
import org.utl.dsm.dsm403.elZarape.modelos.Persona;
import org.utl.dsm.dsm403.elZarape.modelos.Sucursal;
import org.utl.dsm.dsm403.elZarape.modelos.Usuario;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //probarEstadosController();
        //probarCiudadesController();
        //probarCiudadesPorEstado();
        //probarEmpleadosController();
        //probarInsertarEmpleado();
        //probarEmpleadosController();
    }

    private static void probarEstadosController() {
        System.out.println("Probando EstadosController:");
        EstadosController estadosController = new EstadosController();
        
        try {
            List<Estado> estados = estadosController.getAll();
            
            System.out.println("Lista de estados:");
            for (Estado estado : estados) {
                System.out.println("ID: " + estado.getIdEstado() + 
                                   ", Nombre: " + estado.getNombre());
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los estados: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void probarCiudadesController() {
        System.out.println("Probando CiudadesController:");
        CiudadesController ciudadesController = new CiudadesController();
        
        try {
            List<Ciudad> ciudades = ciudadesController.getAll();
            
            System.out.println("Lista de ciudades:");
            for (Ciudad ciudad : ciudades) {
                System.out.println("ID: " + ciudad.getIdCiudad() + 
                                   ", Nombre: " + ciudad.getNombre() + 
                                   ", Estado: " + ciudad.getEstado().getNombre());
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las ciudades: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void probarCiudadesPorEstado() {
        System.out.println("Probando getCiudadesPorEstado:");
        CiudadesController ciudadesController = new CiudadesController();
        
        try {
            int idEstadoPrueba = 1; // Asume que el estado con ID 1 existe
            List<Ciudad> ciudades = ciudadesController.getCiudadesPorEstado(idEstadoPrueba);
            
            System.out.println("Lista de ciudades para el estado con ID " + idEstadoPrueba + ":");
            for (Ciudad ciudad : ciudades) {
                System.out.println("ID: " + ciudad.getIdCiudad() + 
                                   ", Nombre: " + ciudad.getNombre() + 
                                   ", Estado: " + ciudad.getEstado().getNombre());
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las ciudades por estado: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void probarEmpleadosController() {
        System.out.println("Probando EmpleadosController:");
        EmpleadoController empleadoController = new EmpleadoController();
        
        try {
            List<Empleado> empleados = empleadoController.getAll();
            
            System.out.println("Lista de empleados:");
            for (Empleado empleado : empleados) {
                System.out.println("ID: " + empleado.getIdEmpleado() + 
                                 ", Nombre: " + empleado.getPersona().getNombre() +
                                 " " + empleado.getPersona().getApellidos() +
                                 ", Usuario: " + empleado.getUsuario().getNombre() +
                                 ", Sucursal: " + empleado.getSucursal().getNombre() +
                                 ", Ciudad: " + empleado.getPersona().getCiudad().getNombre());
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los empleados: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void probarInsertarEmpleado() {
        System.out.println("Probando insertar empleado:");
        EmpleadoController empleadoController = new EmpleadoController();
        
        try {
            // Crear objetos necesarios
            Persona persona = new Persona();
            persona.setNombre("Juan");
            persona.setApellidos("Pérez García");
            persona.setTelefono("4771234567");
                        
            Ciudad ciudad = new Ciudad();
            ciudad.setIdCiudad(1); // Asegúrate que este ID existe en tu base de datos
            persona.setCiudad(ciudad);
            
            Usuario usuario = new Usuario();
            usuario.setNombre("juanperez");
            usuario.setContrasenia("123456");
            
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(1); // Asegúrate que este ID existe en tu base de datos
            
            // Crear empleado
            Empleado empleado = new Empleado();
            empleado.setPersona(persona);
            empleado.setUsuario(usuario);
            empleado.setSucursal(sucursal);
            
            // Insertar empleado
            int idEmpleado = empleadoController.insert(empleado);
            System.out.println("Empleado insertado exitosamente con ID: " + idEmpleado);
            
            // Mostrar los datos del empleado insertado
            System.out.println("Datos del empleado insertado:");
            System.out.println("ID Empleado: " + empleado.getIdEmpleado());
            System.out.println("ID Persona: " + empleado.getPersona().getIdPersona());
            System.out.println("ID Usuario: " + empleado.getUsuario().getIdUsuario());
            
        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }
}

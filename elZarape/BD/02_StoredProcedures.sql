--  Artifact:   02_StoredProcedures.sql
--  Version:    1.0
--  Date:       2024-10-XX 00:00:00
--  Author:     Ma. Guadalupe Brizuela Gaminio
--  Email:      mbrizuela@utleon.edu.mx

--  -------------------------------------------------------------------------------
--  Version:    1.1
--  Date:       2024-10-30 19:27:00
--  Author:     María del Carmen
--  Email:      mruiz@utleon.edu.mx
--  Comments:   1. Se agrego el parametro idCiudad, en procedimiento almacenado insertarEmpleado
--              2.  Se quitaron paramentros v_activo, var_activoEmpleado 
--                  para su inserción se encuentran con  valor default en 1 al insertarse
--              3.  Se corrigieron los inserts en persona y usuario, en donde se utilizaban parametros
-- 					v_activo, var_activoEmpleado 
-- 				4. Se propone ejemplo de llamada del SP insertarEmpleado
--  -------------------------------------------------------------------------------

USE zarape;
-- Store Procedure para insertar nuevos Empleados.

DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado( /*Datos Personales */
IN var_nombre  			VARCHAR(45),		-- 1
IN var_apellidos 		VARCHAR(45),		-- 2
IN var_telefono 		VARCHAR(45),		-- 3
IN var_idCiudad			INT,				-- 4						
IN var_nombreUsuario 	VARCHAR(65),		-- 5    /*Datos de Usuario*/
IN var_contrasenia 		VARCHAR(65),		-- 6
IN var_idSucursal 		INT,				-- 7 /*Datos empleado*/
OUT var_idPersona 		INT,				-- 8  /* Valores de retorno */
OUT var_idUsuario		INT,				-- 9
OUT var_idEmpleado		INT					-- 10
)
BEGIN
-- Comenzamos insertando los datos de la persona
INSERT INTO persona (nombre,apellidos,telefono,idCiudad) VALUES (var_nombre,var_apellidos,var_telefono, var_idCiudad);
-- Obtenemos el ID de Persona que se generó:
SET var_idPersona = LAST_INSERT_ID();


-- Insertamos los datos del usuario
INSERT INTO usuario(nombre,contrasenia) VALUES (var_nombreUsuario,var_contrasenia);
-- Obtenemos el ID de Usuario que se generó:
SET var_idUsuario = LAST_INSERT_ID();

-- Insertamos los datos del Empleado
INSERT INTO empleado(idSucursal,idPersona,idUsuario) VALUES (var_idsucursal,var_idPersona,var_idUsuario);
-- Obtenemos Id del Empleado que se generó
SET var_idEmpleado = LAST_INSERT_ID();
END
$$
DELIMITER ; 

-- ---------------------------------------------------------
-- Llamada del procedimiento almacenado
-- ---------------------------------------------------------
CALL insertarEmpleado( 'José María','Hernández Fernández','4771234567',350,'administrador','admin',1,@idPersona,@idUsuario,@idEmpleado);


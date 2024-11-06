--  Artifact:   01_DDL_V1.5.sql
--  Version:    1.4
--  Date:       2024-07-15 00:00:00
--  Author:     Roberto Castillo
--  Email:      rcastillo@utleon.edu.mx
--  --------------------------------------------------------------------------
--  Version:    1.5
--  Date:       2024-10-11 07:50:00
--  Author:     Miguel Angel Gil Rios
--  Email:      angel.grios@gmail.com / mgil@utleon.edu.mx
--  Comments:   1.  El nombre de la BD cambio de 
--                  [Restaurante] a [zarape].
--              2.  Se cambiaron el nombre de la BD y las tablas
--                  a nombres en minusculas, ya que asi
--                  lo requiere el estandar de MySQL.
--              3.  Se quitaron caracteres no validos en nombres
--                  de tablas y campos. Por ejemplo, el campo [contraseña] 
--                  se cambio por [contrasenia].
--              4.  Se eliminaron las restricciones (CONSTRAINTs)
--                  de llave primaria y se indico dentro de la
--                  declaracion del atributo de llave primaria.
--              5.  Se agrego la directiva AUTO_INCREMENT  a todas las
--                  declaraciones de atributos de tipo llave primaria.
--              6.  Se elimino la propiedad PRIMARY KEY de la tabla tarjeta.
--                  Aunque el numero de tarjeta es unico, no debe ser utilizado
--                  como llave primaria.
--              7.  Al campo del numero de tarjeta se le agrego la restriccion
--                  UNIQUE para disparar errores cuando se introduce un numero
--                  de tarjeta que ya existe.
--              8.  El tamanio de los campos VARCHAR(2) se cambio a
--                  VARCHAR(2) porque MySQL considera el caracter especial
--                  \0 de fin de linea. Esto se debe a que MySQL esta escrito 
--                  en el lenguaje C y heredo esa caracteristica.
--              9.  Se altero el campo [pagado] de la tabla ticket para que
--                  no acepte el valor <NULL> y por defecto tenga 'N'.
--             10.  Se eliminaron los campos [subtotal], [iva] y [total] de
--                  la tabla [ticket] porque son campos calculados y de acuerdo
--                  a la 3ra. F.N. los campos calculados se definen en las vistas.
--             11.  Se eliminaron las tablas calle y colonia, porque no estaban
--                  enlazadas entre si y carecian de sentido. 
--                  En su lugar, se agregaron los campos [calle] y [colonia].
--             12.  Se agregaron las tablas [alimento] y [bebida] y se relacionaron
--                  con la tabla [producto].
--             13.  La longitud del campo [nombre] en la tabla [ciudad] se cambio
--                  de 45 a 128 caracteres.
--  -------------------------------------------------------------------------------
--  Version:    1.6
--  Date:       2024-10-20 19:15:00
--  Author:     Miguel Angel Gil Rios
--  Email:      angel.grios@gmail.com / mgil@utleon.edu.mx
--  Comments:   1.  La tabla [sucursal] se relaciono con la tabla
--                  [ciudad] mediante una F.K.
--              2.  La tabla persona se relaciono con la tabla
--                  [ciudad] mediante una F.K.
--              3.  El tamaño del campo [horarios] de la tabla [sucursal] 
--                  se extendio a un tamanio de 255 caracteres.
--  -------------------------------------------------------------------------------

DROP DATABASE zarape;

CREATE DATABASE zarape;

USE zarape;

-- -----------------------------------------------------
-- Table estado
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS estado
(
    idEstado INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre varchar(45)
);

-- -----------------------------------------------------
-- Table ciudad
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ciudad 
(
    idCiudad INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(129),
    idEstado INT,
    CONSTRAINT fk_ciudad_estado_idEstado FOREIGN KEY (idEstado) REFERENCES estado(idEstado)
) ;

-- -----------------------------------------------------
-- Table sucursal
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sucursal 
(
    idSucursal    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre        VARCHAR(65)  NOT NULL,
    latitud       VARCHAR(65)  NOT NULL DEFAULT '',
    longitud      VARCHAR(65)  NOT NULL DEFAULT '',
    foto          LONGTEXT,
    urlWeb        VARCHAR(65)  NULL DEFAULT '',
    horarios      VARCHAR(255) NULL DEFAULT '',
    calle         VARCHAR(65)  NOT NULL DEFAULT '',
    numCalle      VARCHAR(65)  NOT NULL,
    colonia       VARCHAR(65)  NOT NULL DEFAULT '',
    idCiudad      INT NOT NULL, -- No es necesario el estado, con la ciudad se puede saber.
    activo        INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_sucursal_ciudad FOREIGN KEY (idCiudad) REFERENCES ciudad(idCiudad)
) ;

-- -----------------------------------------------------
-- Table persona
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS persona 
(
    idPersona INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre    VARCHAR(45) NULL,
    apellidos VARCHAR(45) NULL,
    telefono  VARCHAR(45) NULL,
    idCiudad  INT NOT NULL,
    CONSTRAINT fk_persona_ciudad FOREIGN KEY (idCiudad) REFERENCES ciudad(idCiudad)
) ;

-- -----------------------------------------------------
-- Table usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario 
(
    idUsuario   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR(65) NULL,
    contrasenia VARCHAR(65) NULL,
    activo      INT NOT NULL DEFAULT 1
) ;

-- -----------------------------------------------------
-- Table empleado
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS empleado 
(
    idEmpleado  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idSucursal  INT NOT NULL,
    idPersona   INT NOT NULL,
    idUsuario   INT NOT NULL,
    activo      INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_empleado_sucursal FOREIGN KEY (idSucursal) REFERENCES sucursal (idSucursal),
    CONSTRAINT fk_empleado_persona FOREIGN KEY (idPersona) REFERENCES persona (idPersona),
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario)
 ) ;


-- -----------------------------------------------------
-- Table cliente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente 
(
    idCliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idPersona INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT 1,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (idPersona) REFERENCES persona (idPersona)
) ;

-- -----------------------------------------------------
-- Table tarjeta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tarjeta 
(
    idTarjeta INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    titular   VARCHAR(64) NULL, -- Nombre del titular
    numero    VARCHAR(16) NOT NULL,
    yy        VARCHAR(3) NULL,
    mm        VARCHAR(3) NULL,
    cvv       VARCHAR(4) NOT NULL DEFAULT '',
    calle     VARCHAR(65) NOT NULL DEFAULT '',
    numCalle  VARCHAR(7)  NOT NULL DEFAULT '',
    colonia   VARCHAR(65) NOT NULL DEFAULT '',
    cp        VARCHAR(7) NULL,
    activo    INT NOT NULL DEFAULT 1,
    idCliente INT NOT NULL,
    idEstado  INT NOT NULL, -- La tarjeta se enlaza solo con la entidad
    CONSTRAINT fk_tarjeta_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente),
    CONSTRAINT fk_tarjeta_estado FOREIGN KEY (idEstado) REFERENCES estado(idEstado)    
);

-- -----------------------------------------------------
-- Table categoria
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categoria 
(
    idCategoria INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(45) NULL,
    tipo        VARCHAR(2) NULL DEFAULT 'A' COMMENT 'Puede ser A de Alimentos o B de Bebidas U OTRO',
    activo      INT NOT NULL DEFAULT 1
);

-- -----------------------------------------------------
-- Table producto
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS producto 
(
    idProducto  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR(45) NULL,
    descripcion VARCHAR(45) NULL,
    foto        LONGTEXT NULL,
    precio      DECIMAL(2) NULL,
    idCategoria INT NOT NULL,
    activo      INT NOT NULL DEFAULT 1,
    CONSTRAINT  fk_producto_categoria FOREIGN KEY (idCategoria) REFERENCES categoria (idCategoria)
);

CREATE TABLE alimento
(
    idAlimento  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idProducto  INT,
    CONSTRAINT  fk_alimento_producto FOREIGN KEY (idProducto) REFERENCES producto (idProducto)
);

CREATE TABLE bebida
(
    idBebida    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idProducto  INT,
    CONSTRAINT  fk_bebida_producto FOREIGN KEY (idProducto) REFERENCES producto (idProducto)
);


-- -----------------------------------------------------
-- Table combo
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS combo 
(
    idCombo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45) NULL,
    total DECIMAL(2) NOT NULL
) ;

-- -----------------------------------------------------
-- Table detalle_combo
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS detalle_combo 
(    
    idCombo     INT NOT NULL,
    idProducto  INT NOT NULL,
    precio      DECIMAL(2) NOT NULL,
    CONSTRAINT  fk_detallecombo_producto FOREIGN KEY (idProducto) REFERENCES producto (idProducto),
    CONSTRAINT  fk_detallecombo_combo FOREIGN KEY (idCombo) REFERENCES combo (idCombo)
 );

-- -----------------------------------------------------
-- Table ticket
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ticket 
(
    idTicket    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ticket      VARCHAR(2) NULL DEFAULT 'S' COMMENT 'Este campo se registra si es una orden en la sucursal o si es un pedido de casa',
    fecha       DATE NULL,
    pagado      VARCHAR(2) NOT NULL DEFAULT 'N' COMMENT 'S: Si; N: No;',
    idCliente   INT NOT NULL,
    idSucursal  INT NOT NULL,
    estatus     INT NOT NULL DEFAULT 1, -- 0: Cancelado; 1: En servicio; 2: Pagado
    CONSTRAINT fk_ticket_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente),
    CONSTRAINT fk_ticket_sucursal FOREIGN KEY (idSucursal) REFERENCES sucursal (idSucursal)
) ;


-- -----------------------------------------------------
-- Table detalle_ticket
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS detalle_ticket 
(
    idTicket    INT NOT NULL,
    cantidad    INT NULL,
    precio      DECIMAL(2) NULL, -- Precio del Producto
    idCombo     INT,
    idProducto  INT,
    CONSTRAINT  fk_detalle_ticket_ticket FOREIGN KEY (idTicket) REFERENCES ticket (idTicket),
    CONSTRAINT  fk_detalle_ticket_combo FOREIGN KEY (idCombo) REFERENCES combo (idCombo),
    CONSTRAINT  fk_detalle_ticket_producto FOREIGN KEY (idProducto) REFERENCES producto (idProducto)
);

-- -----------------------------------------------------
-- Table comanda
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS comanda
(
    idComanda   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idTicket    INT NOT NULL,
    estatus     INT NOT NULL DEFAULT 1, -- 0: Cancelado; 
                                        -- 1: En servicio; 
                                        -- 2: Lista (se emite alarma)
                                        -- 3: Entregada    
    CONSTRAINT fk_comanda_ticket FOREIGN KEY (idTicket) REFERENCES ticket (idTicket)
);
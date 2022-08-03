package conex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 Paso 0:   Crear un nuevo database y una tabla llamada persona con los campos(id, nombre, apellido, edad).
*/
public class Conexion2 {
    //1.Primer Paso: Crear Atributo de tipo Connection
    private Connection conn = null;
    //2.Segundo Paso: Crear Constructor
    public Conexion2() throws SQLException {
        try {
            connect();
            consulta2();
            //Se van a llamar los métodos para rectificar su uso...
        } finally {
            closeConnection();
        }
    }
    
    //3.Tercer paso, crear método para conectarse a la base de datos
    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemploProjectSwing", "root", "password");
       
        //conn.setAutoCommit(false);//Controlar manualmente cuando se hace un commit..
        //conn.commit();
        //conn.rollback();
        //conn.setSavepoint();
    }
    //4.Cuart paso, crear método para conectarse a la base de datos
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
    //5.Quinto paso, crear método Main para realizar la llamada de la clase y realizar la ejecución..
    public static void main(String[] args) {
        try {
            new Conexion2();
        } catch (SQLException e) {
            System.out.println("Hubo problemas al momento de conectarse: " + e.getMessage());
        }
    }
    //6.Sexto paso, se crea el método consultar(Revisar en SQL la sentencia)
    public void consulta() throws SQLException {
        //1.Creamos un objeto de tipo Stament(declaración)que sirve para crear sentencias SQL...
        Statement statement = conn.createStatement();
        //2.Creamos objeto ResultSet para poder recorrer los datos de la tabla.
        ResultSet set = statement.executeQuery("SELECT * FROM persona");
        //3.Recorremos el ResultSet con next el cual va a devolver true mientras que exitan registros..
        while (set.next()) {
            int idPersona = set.getInt("id");
            String nombre = set.getString("nombre");
            String apellido = set.getString("apellido");
            int edad = set.getInt("edad");
            System.out.println("Persona " + idPersona + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Edad: " + edad);
        }
        //4.Es necesario cerrar el Result Set y el Statement... 
        //Ya que el recolector de basura de Java no se lleva bien con las implementaciones de JDBC....
        set.close();
        statement.close();
        //SE PUEDE HACER ADICIONAL EL TEMA DEL OBJETO CON LO CUAL, DEVUELVA UNA LISTA DE PERSONAS..
    }

    
    //7.Septimo paso, se crea el método consultar por Apellido o Nombre o Edad(Revisar en SQL la sentencia)
    
    public void consultaPorApellido() throws SQLException {
        //1.Creamos un objeto de tipo Stament(declaración)que sirve para crear sentencias SQL...
        Statement statement = conn.createStatement();
        //2.Creamos objeto ResultSet para poder recorrer los datos de la tabla.
        ResultSet set = statement.executeQuery("SELECT * FROM persona");
        //3.Recorremos el ResultSet con next el cual va a devolver true mientras que exitan registros..
        while (set.next()) {
            int idPersona = set.getInt("id");
            String nombre = set.getString("nombre");
            String apellido = set.getString("apellido");
            int edad = set.getInt("edad");
            System.out.println("Persona " + idPersona + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Edad: " + edad);
        }
        //4.Es necesario cerrar el Result Set y el Statement... 
        //Ya que el recolector de basura de Java no se lleva bien con las implementaciones de JDBC....
        set.close();
        statement.close();
    }    
    
    
    //8.Octavo paso, se crea nuevamente el método con la implementación de PrepareStatement
    /*
    Importante: 
        Se debe tener cuidado con las entradas para una consulta de base de datos y mas si estas estan expuestas al usuario ya que puede ser la
        aplicación vulnerable a todo tipo de ataques.
        Por lo que toda entrada para una consulta de base de datos es potencialmente peligrosa.
        Se tiene que rectificar que el valor de entrada tiene que ser seguro.
        Para esto la la libreria tiene como poder resolverlo para hacer limpieza de los parametros.
        Llega a ser mas seguro para evitar cualquier tipo de vulnerabilidad al inyectar sql.
     */
    public void consultaPorApellido(String ape) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM persona WHERE apellido = ?");
        statement.setString(1, ape);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            int idPersona = set.getInt("id");
            String nombre = set.getString("nombre");
            String apellido = set.getString("apellido");
            int edad = set.getInt("edad");
            System.out.println("Persona " + idPersona + ", Nombre: " + nombre + ", Apellido: " + apellido + ", Edad: " + edad);
        }
        set.close();
        statement.close();
    }
    //9.Noveno Paso, se crea método agregarPersona(Revisar primero sentencia SQL)
    public void agregarPersona(String nombre, String apellido, int edad) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO persona (nombre, apellido, edad) VALUE (?,?,?)");
        statement.setString(1, nombre);
        statement.setString(2, apellido);
        statement.setInt(3, edad);
        //Use this method to execute SQL statements, for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.
        System.out.println("Cuantas registros fueron actualizados: " + statement.executeUpdate());
        statement.close();
        System.out.println("Ejecutado Agregar");
        //SE PUEDE AGREGAR EL TEMA QUE EL MÉTODO PUEDE DEVOLVER TRUE O FALSE PARA POSTERIORMENTE NOTIFICAR SI SE HIZO EL CAMBIO O NO..
    }
    //10.Decimo paso, se crea método eliminarPersonaPorNombre(Revisar primero sentencia SQL)
    public void eliminarPersonaPorNombre(String nombre) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM persona WHERE nombre = ?");
        statement.setString(1, nombre);
        //Use this method to execute SQL statements, for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.
        System.out.println("Cuantas registros fueron actualizados: " + statement.executeUpdate());
        statement.close();
        System.out.println("Ejecutado Eliminar");

    }
    //11.Onceabo paso, se crea método actualizarPersona(Revisar primero sentencia SQL) Aprovechamos de crear el objeto Persona.
    public void actualizarPersonaPorNombre(int id,Persona persona) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE persona SET nombre = ?,apellido = ?,edad = ? WHERE id = ?");
        statement.setInt(4, id);
        statement.setString(1, persona.getNombre());
        statement.setString(2, persona.getApellido());
        statement.setInt(3, persona.getEdad());
        //Use this method to execute SQL statements, for which you expect to get a number of rows affected - for example, an INSERT, UPDATE, or DELETE statement.
        System.out.println("Cuantas registros fueron actualizados: " + statement.executeUpdate());
        statement.close();
        System.out.println("Ejecutado Actualizar");

    }
    //12.Doceabo paso, mostrar una transacción(Revisar primero sentencia y significado) Actualizar connect
    public void transaccion() throws SQLException {
        String persona1 = "INSERT INTO persona (id, nombre, apellido, edad) VALUES (?,?,?,?)";
        String persona2 = "INSERT INTO persona (id, nombre, apellido, edad) VALUES (?,?,?,?)";
        PreparedStatement statement1 = null, statement2 = null;
        try {
            statement1 = conn.prepareStatement(persona1);
            statement1.setInt(1, 14);
            statement1.setString(2, "asdasd");
            statement1.setString(3, "Perez");
            statement1.setInt(4, 29);
            statement1.executeUpdate();
            //Nosotros no deseamos un result Set, en este caso queremos recibir el valor de número de columnas manipuladas
            statement2 = conn.prepareStatement(persona2);
            statement2.setInt(1, 11);
            statement2.setString(2, "Diana");
            statement2.setString(3, "Sanchez");
            statement2.setInt(4, 31);
            statement2.executeUpdate();

            conn.commit();//CONFIRMA LOS CAMBIOS EN LA BASE DE DATOS...
            System.out.println("Ejecutado Transacción!!");
        } catch (SQLException e) {
            conn.rollback();//REVIERTE LOS CAMBIOS REALIZADOS POR EL BLOQUE DE EJECUCIÓN...
            e.printStackTrace();
        } finally {
            if (statement1 != null) {
                statement1.close();
            }
            if (statement2 != null) {
                statement2.close();
            }
        }
    }

public void consulta2() throws SQLException {
        //1.Creamos un objeto de tipo Stament(declaración)que sirve para crear sentencias SQL...
        Statement statement = conn.createStatement();
        //2.Creamos objeto ResultSet para poder recorrer los datos de la tabla.
        ResultSet set = statement.executeQuery("SELECT p.nombre, p.apellido, pro.nombre FROM PERSONA p INNER JOIN PRODUCTO pro ON p.id = pro.creado_por order by p.nombre");
        //3.Recorremos el ResultSet con next el cual va a devolver true mientras que exitan registros..
        while (set.next()) {
            System.out.println(set.getString("p.nombre"));
            System.out.println(set.getString("p.apellido"));
            System.out.println(set.getString("pro.nombre"));
        }
        //4.Es necesario cerrar el Result Set y el Statement... 
        //Ya que el recolector de basura de Java no se lleva bien con las implementaciones de JDBC....
        set.close();
        statement.close();
        //SE PUEDE HACER ADICIONAL EL TEMA DEL OBJETO CON LO CUAL, DEVUELVA UNA LISTA DE PERSONAS..
    }    

}

package dao.mysql;

import Exception.DAOException;
import Modelo.Persona;
import dao.PersonaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//PASO 4: IMPLEMENTACIÓN DE LA INTERFAZ
public class MysqlPersonaDAO implements PersonaDAO {

    final String INSERT = "INSERT INTO persona(nombre, apellido, edad) VALUE (?,?,?)";
    final String UPDATE = "UPDATE persona SET nombre = ?, apellido = ?, edad = ? WHERE  id = ?";
    final String DELETE = "DELETE FROM persona WHERE id = ?";
    final String GET_ALL = "SELECT * FROM persona";
    final String GET_ONE = "SELECT * FROM persona WHERE id = ?";

    private Connection conn;

    public MysqlPersonaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Persona p) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, p.getName());
            stat.setString(2, p.getApellido());
            stat.setInt(3, p.getEdad());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("No se guardo en la base de datos..");
            }
        } catch (SQLException e) {//IR A PASO 5: CREACIÓN DE EXCEPTION...
            throw new DAOException("Error en SQL: ", e);
        } finally {
           cerrarStatement(stat);
        }
    }

    @Override
    public void modificar(Persona p) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, p.getName());
            stat.setString(2, p.getApellido());
            stat.setInt(3, p.getEdad());
            stat.setLong(4, p.getId());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("No se actualizo en la base de datos..");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL: ", e);
        } finally {
            cerrarStatement(stat);
        }
    }

    @Override
    public void eliminar(Persona p) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(DELETE);
            stat.setLong(1, p.getId());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("No se elimino en la base de datos..");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL: ", e);
        } finally {
            cerrarStatement(stat);
        }
    }

    @Override
    public List<Persona> obtenerPersonas() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Persona> per = new ArrayList<>();
        try {
            stat = conn.prepareStatement(GET_ALL);
            rs = stat.executeQuery();
            while (rs.next()) {
               per.add(convertir(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL: ", e);
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stat);
        }
        return per;
    }

    @Override
    public Persona obtenerPorId(Long id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Persona per = null;
        try {
            stat = conn.prepareStatement(GET_ONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                per = convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado ningun registro.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL: ", e);
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stat);
        }
        return per;
    }

    private Persona convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        int edad = rs.getInt("edad");
        Persona persona = new Persona(nombre, apellido, edad);
        persona.setId(rs.getLong("id"));
        return persona;
    }
    
    private void cerrarStatement(PreparedStatement stat) throws DAOException{
        if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DAOException("Error en SQL: ", e);
                }
            }
    }
    
    private void cerrarResultSet(ResultSet rs) throws DAOException{
        if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error en SQL: ", e);
                }
            }
    }
    
    //PASO 6: Para realizar pruebas del desarrollo
    /*public static void main(String[] args) throws SQLException, DAOException {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemploConexion", "root", "Mysql26*");
            //Persona p = new Persona("Nuevo","Persona",33);
            
            PersonaDAO dao = new MysqlPersonaDAO(conn);
            //dao.insertar(p);
            List<Persona> personas = dao.obtenerPersonas();
            for(Persona p : personas){
                System.out.println(p.toString());
            }
        }finally{
            if(conn != null){
                conn.close();
            }
            
        }
    }*/

}

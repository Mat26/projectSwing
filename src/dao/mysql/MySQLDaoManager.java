package dao.mysql;

import dao.DAOManager;
import dao.PersonaDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//PASO 8: CREACIÓN DE MYSQLDAOMANAGER e implementar DAOMANAGER...
public class MySQLDaoManager implements DAOManager{

    private Connection conn;
    private PersonaDAO persona=null;

    public MySQLDaoManager(String host, String database, String username, String password) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        if(persona == null){
            persona = new MysqlPersonaDAO(conn);
        }
        return persona;
    }
    
}

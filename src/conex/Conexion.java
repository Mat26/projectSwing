
package conex;
//https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.29

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    public static void main(String[] args) {
        connect();
    }
    //First step: Add Driver in the project.
    //Secod step: Create Class where we will create the connection.
    //Third step: Create method to connect with the data base.
    public static void connect(){
        //There are different ways to establish a connection, but here is the initial one.
        Connection conn = null;
        //It may throw an exception, so it is important to use Try catch...
        
        //Class.forName("com.mysql.jdbc.Driver");//Required in JDBC 4 or lesss        
        
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemploProjectSwing","root","password");
                                               //Connection route with JDBC, User , password...
            if(conn!=null){
                System.out.println("Conexion realizada con exito.");
            }
        }catch(SQLException e){
            System.out.println("Ha ocurrido un error, "+ e.getMessage());
        }finally {
            if(conn!=null){
                try{
                conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author David
 */
public class Conexion {
    Connection conn= null;
    String hostname=null;
    String port =null;
    String database =null;
    String username = null;
    String password = null;
    String jndi=null;
    String driver= null;
    static Properties props = new Properties();
    
    public Conexion (){
    InputStream ext = null;
    try{
     ext = Files.newInputStream(FileSystems.getDefault().getPath("c:\\facturacion\\Base.props"));
     props.load(ext);
     ext.close();
    }
    catch (IOException ex){
        ex.printStackTrace();
    }
    
    // Invocar un metodo para cargar info en memoria. 
    cargarPropiedades();
    
    
    }
    
    public void cargarPropiedades(){
        this.hostname= props.getProperty("hostname");
        this.port= props.getProperty("port");
        this.database=props.getProperty("database");
        this.username=props.getProperty("username");
        this.password=props.getProperty("password");
        this.driver=props.getProperty("driver");
        jndi= props.getProperty("jndi");

    }
    
    
    public Connection getConection() throws SQLException{     
                try{
                    Class.forName(driver);
                    String jdbcUrl = "jdbc:mysql://"+hostname+":"+port+"/"+database+"?useTimezone=true&serverTimezone=UTC";  
                    conn = DriverManager.getConnection(jdbcUrl,username,password);
                    System.out.println("Se ha Establecido la Conexion con la BD");
                }catch(Exception e){
                   System.out.println("Error al registrar el driver de MySQL: " + e);
                }
                   
        return conn;
    }
    public void CerrarConection(){
    try{
        conn.close();
        System.out.println("La sesion se cerro exitosamente");
    }catch(SQLException e){
        System.out.println("Error al cerrar la conexion");
    
    }
    }
    
}

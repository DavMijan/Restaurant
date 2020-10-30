/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;
import Visual.Principal;
import Conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author David
 */
public class Productos {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    
    public void LimpiarCampos()
    {
        Principal.Id_Producto.setText("");
        Principal.Producto.setText("");
        Principal.Precio.setText("");
    }
    
    public void NuevoProducto()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call NuevoMenu(?,?,?)}");
            ps.setString(1, Principal.Id_Producto.getText());
            ps.setString(2, Principal.Producto.getText());
            ps.setString(3, Principal.Precio.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha Agregado correctamente el Producto");
            LimpiarCampos();
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            LimpiarCampos();
        }
    }
    
    public void BuscarProducto()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarMenu(?)}");
            ps.setString(1, Principal.Id_Producto.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Principal.Id_Producto.setText(r.getString("Id_Producto"));
                Principal.Producto.setText(r.getString("Producto"));
                Principal.Precio.setText(r.getString("Precio"));
                JOptionPane.showMessageDialog(null, "Se ha Encontrado el Producto");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro el Producto");
                LimpiarCampos();
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}

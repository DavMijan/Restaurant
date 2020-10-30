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
public class Clientes {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;

    public void LimpiarCampos()
    {
        Principal.ID_Cliente.setText("");
        Principal.Nombre_Cliente.setText("");
        Principal.Apellido_Cliente.setText("");
        Principal.Correo_Cliente.setText("");
        Principal.NIT_Cliente.setText("");
    }
    
    public void NuevoCliente()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call NuevoCliente(?,?,?,?,?)}");
            ps.setString(1, Principal.ID_Cliente.getText());
            ps.setString(2, Principal.Nombre_Cliente.getText());
            ps.setString(3, Principal.Apellido_Cliente.getText());
            ps.setString(4, Principal.Correo_Cliente.getText());
            ps.setString(5, Principal.NIT_Cliente.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha Agregado correctamente el Cliente");
            LimpiarCampos();
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            LimpiarCampos();
        }
    }
    
    public void BuscarCliente()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarCliente(?)}");
            ps.setString(1, Principal.ID_Cliente.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Principal.ID_Cliente.setText(r.getString("Id_Cliente"));
                Principal.Nombre_Cliente.setText(r.getString("Nombre_Cliente"));
                Principal.Apellido_Cliente.setText(r.getString("Apellido_Cliente"));
                Principal.Correo_Cliente.setText(r.getString("Correo"));
                Principal.NIT_Cliente.setText(r.getString("NIT"));
                JOptionPane.showMessageDialog(null, "Se ha Encontrado el Cliente");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro el Cliente");
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

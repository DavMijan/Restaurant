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
public class Mesas {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    
    public void LimpiarCampos()
    {
        Principal.No_Mesa.setText("");
        Principal.Mesa.setText("");
        Principal.No_Ocupantes.setText("");
    }
    
    public void NuevaMesa()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call NuevaMesa(?,?,?)}");
            ps.setString(1, Principal.No_Mesa.getText());
            ps.setString(2, Principal.Mesa.getText());
            ps.setString(3, Principal.No_Ocupantes.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha Agregado correctamente la Mesa");
            LimpiarCampos();
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            LimpiarCampos();
        }
    }
    
    public void BuscarMesa()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarMesa(?)}");
            ps.setString(1, Principal.No_Mesa.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Principal.No_Mesa.setText(r.getString("No_Mesa"));
                Principal.Mesa.setText(r.getString("Mesa"));
                Principal.No_Ocupantes.setText(r.getString("Tamano"));
                JOptionPane.showMessageDialog(null, "Se ha Encontrado la Mesa");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro la Mesa");
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

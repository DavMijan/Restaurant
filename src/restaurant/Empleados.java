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
public class Empleados {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    
    public void LimpiarCampos()
    {
        Principal.Id_Empleado.setText("");
        Principal.Nombre_Empleado.setText("");
        Principal.Apellido_Empleado.setText("");
        Principal.DPI_Empleado.setText("");
    }
    
    public void NuevoEmpleado()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call NuevoEmpleado(?,?,?,?)}");
            ps.setString(1, Principal.Id_Empleado.getText());
            ps.setString(2, Principal.Nombre_Empleado.getText());
            ps.setString(3, Principal.Apellido_Empleado.getText());
            ps.setString(4, Principal.DPI_Empleado.getText());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha Agregado correctamente el Empleado");
            LimpiarCampos();
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            LimpiarCampos();
        }
    }
    
    public void BuscarEmpleado()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarEmpleado(?)}");
            ps.setString(1, Principal.Id_Empleado.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Principal.Id_Empleado.setText(r.getString("Id_Empleado"));
                Principal.Nombre_Empleado.setText(r.getString("Nombre_Empleado"));
                Principal.Apellido_Empleado.setText(r.getString("Apellido_Empleado"));
                Principal.DPI_Empleado.setText(r.getString("DPI"));
                JOptionPane.showMessageDialog(null, "Se ha Encontrado el Empleado");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro el Empleado");
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

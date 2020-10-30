/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import Conexion.Conexion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static Visual.Principal.Consultas;

/**
 *
 * @author David
 */
public class Consultas {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    
    public void VerEmpleados()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call CatEmpleado()}");
            r=ps.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            Consultas.setModel(modelo);
            modelo.addColumn("Id Empleado");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("DPI");
            while (r.next())
            {
                Object [] fila = new Object[4];
                for (int i=0;i<4;i++)
                fila[i] = r.getObject(i+1); 
                modelo.addRow(fila);
            }
            JOptionPane.showMessageDialog(null, "Catalogo Actualizado");
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void VerClientes()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call CatCliente()}");
            r=ps.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            Consultas.setModel(modelo);
            modelo.addColumn("Id Cliente");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Correo");
            modelo.addColumn("NIT");
            while (r.next())
            {
                Object [] fila = new Object[5];
                for (int i=0;i<5;i++)
                fila[i] = r.getObject(i+1); 
                modelo.addRow(fila);
            }
            JOptionPane.showMessageDialog(null, "Catalogo Actualizado");
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void VerProductos()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call CatMenu()}");
            r=ps.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            Consultas.setModel(modelo);
            modelo.addColumn("Id Producto");
            modelo.addColumn("Producto");
            modelo.addColumn("Precio");
            while (r.next())
            {
                Object [] fila = new Object[3];
                for (int i=0;i<3;i++)
                fila[i] = r.getObject(i+1); 
                modelo.addRow(fila);
            }
            JOptionPane.showMessageDialog(null, "Catalogo Actualizado");
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void VerMesas()
    {
        try 
        {
            conn =conex.getConection();
            ps= (CallableStatement) conn.prepareCall("{Call CatMesa()}");
            r=ps.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            Consultas.setModel(modelo);
            modelo.addColumn("No. Mesa");
            modelo.addColumn("Mesa");
            modelo.addColumn("Tamaño (Personas)");
            while (r.next())
            {
                Object [] fila = new Object[3];
                for (int i=0;i<3;i++)
                fila[i] = r.getObject(i+1); 
                modelo.addRow(fila);
            }
            JOptionPane.showMessageDialog(null, "Catalogo Actualizado");
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

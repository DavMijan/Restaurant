/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import Visual.Principal;
import Visual.GenerarBoletas;
import Conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class Facturas {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    ArrayList Productos = new ArrayList();
    ArrayList CodProductos = new ArrayList();
    ArrayList Precios = new ArrayList();
    DefaultListModel modelo = new DefaultListModel();
    DefaultListModel modelo2 = new DefaultListModel();
    DefaultListModel modelo3 = new DefaultListModel();
    String Produc;
    String Estado = "Pedido";
    String Nombre_Cli;
    String Apellido_Cli;
    String Nombre_Emp;
    String Apellido_Emp;
    String CodProdFinal;
    String ProdFinal;
    String Precio;   
    int Total;
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////
    public void AñadirProducto()
    {
        try 
        {
            conn =conex.getConection();
            GenerarBoletas.jList1.setModel(modelo);
            GenerarBoletas.jList2.setModel(modelo2);          
            GenerarBoletas.jList3.setModel(modelo3);          
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarProductos(?)}");
            ps.setString(1, GenerarBoletas.Id_Producto_Or.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                CodProductos.add(GenerarBoletas.Id_Producto_Or.getText());
                GenerarBoletas.Prod.setText(r.getString("Producto"));
                Total=Total+Integer.parseInt(r.getString("Precio"));
                Produc = GenerarBoletas.Prod.getText();  
                Precios.add(r.getString("Precio"));
                Productos.add(Produc);
                modelo.removeAllElements();
                modelo2.removeAllElements();
                modelo3.removeAllElements();
                modelo.addAll(CodProductos);
                modelo2.addAll(Productos);
                modelo3.addAll(Precios);
                JOptionPane.showMessageDialog(null, "Se ha Agregado el Producto");
                GenerarBoletas.Id_Producto_Or.setText("");
                GenerarBoletas.Prod.setText("");
                GenerarBoletas.TotalOrden.setText(String.valueOf(Total));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el Producto");
                GenerarBoletas.Id_Producto_Or.setText("");
                GenerarBoletas.Prod.setText("");
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void BuscarClientes()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarCliente(?)}");
            ps.setString(1, GenerarBoletas.Id_Cliente_Or.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Nombre_Cli = r.getString("Nombre_Cliente");
                Apellido_Cli = r.getString("Apellido_Cliente");
                JOptionPane.showMessageDialog(null, "Si Existe el Cliente");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro el Cliente");
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void BuscarEmpleados()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarEmpleados(?)}");
            ps.setString(1, GenerarBoletas.Id_Empleado_Or.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Nombre_Emp = r.getString("Nombre_Empleado");
                Apellido_Emp = r.getString("Apellido_Empleado");
                JOptionPane.showMessageDialog(null, "Si Existe el Empleado");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro el Empleado");
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
        /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void BuscarMesa()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarMesa(?)}");
            ps.setString(1, GenerarBoletas.Mesa_Ord.getText());
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
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void Generar()
    {
        for (int x = 0; x < CodProductos.size(); x++) 
        {
            CodProdFinal = (String) CodProductos.get(x);
            System.out.println(CodProdFinal);
        
            try 
            {
                conn =conex.getConection();
                ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarProductos(?)}");
                ps.setString(1, CodProdFinal);
                r=ps.executeQuery();
                if (r.next())
                {
                    ProdFinal = r.getString("Producto");
                    Precio = r.getString("Precio");
                }
                else
                {
                JOptionPane.showMessageDialog(null, "No se encontro el Producto");
                }
                conex.CerrarConection();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex);
            }
        
            try 
            {
                conn =conex.getConection();
                ps= (CallableStatement) conn.prepareCall("{Call NuevaFactura(?,?,?,?,?,?,?,?,?,?,?,?)}");
                ps.setString(1, GenerarBoletas.Id_Cliente_Or.getText());
                ps.setString(2, Nombre_Cli);
                ps.setString(3, Apellido_Cli);
                ps.setString(4, GenerarBoletas.Cod_Pedido.getText());
                ps.setString(5, GenerarBoletas.Id_Empleado_Or.getText());
                ps.setString(6, Nombre_Emp);
                ps.setString(7, Apellido_Emp);
                ps.setString(8, GenerarBoletas.Mesa_Ord.getText());
                ps.setString(9, CodProdFinal);
                ps.setString(10, ProdFinal);
                ps.setString(11, Precio);
                ps.setString(12, Estado);
                ps.execute();
                conex.CerrarConection();
            } 
            catch (SQLException ex) {
                Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex);
            }
        
        }
    }
}

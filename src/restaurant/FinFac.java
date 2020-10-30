/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;
import Visual.FactsMesa;
import Visual.Facts;
import Conexion.Conexion;
import static Visual.Facts.CodPedido;
import static Visual.FactsMesa.CodMesa;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author David
 */
public class FinFac {
    Conexion conex = new Conexion();
    Connection conn = null;
    ResultSet r;
    String data;
    CallableStatement ps;
    String Estado= "Pagada";
    String Id_Cliente;
    String Nombre_Cliente;
    String Apellido_Cliente;
    String Codigo_Pedido;
    String Id_Empleado;
    String Nombre_Empleado;
    String Apellido_Empleado;
    String No_Mesa;
    int Total;
    
    //Codigo de Soporte Para Generar Factura Cancelada
    public void PagarOrden()
    {
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call PagarFactura(?,?)}");
            ps.setString(1, Facts.CodPedido.getText());
            ps.setString(2, Estado);
            ps.execute();
            r=ps.executeQuery();
            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        try 
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarFactura(?)}");
            ps.setString(1, Facts.CodPedido.getText());
            r=ps.executeQuery();
            if (r.next())
            {
                Id_Cliente = (r.getString("Id_Cliente"));
                Nombre_Cliente= (r.getString("Nombre_Cliente"));
                Apellido_Cliente= (r.getString("Apellido_Cliente"));
                Codigo_Pedido= (r.getString("Codigo_Pedido"));
                Id_Empleado= (r.getString("Id_Empleado"));
                Nombre_Empleado= (r.getString("Nombre_Empleado"));
                Apellido_Empleado= (r.getString("Apellido_Empleado"));
                No_Mesa= (r.getString("No_Mesa"));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro la factura");
            }

            conex.CerrarConection();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Restaurant.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //Codigo Para Generar Factura Cancelada
    public void GenerarPDF() throws FileNotFoundException, DocumentException, IOException
    {
        try
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarFactura(?)}");
            ps.setString(1, Facts.CodPedido.getText());
            String Factura=Facts.CodPedido.getText();
            r=ps.executeQuery();
            if (r.next())
            {
            OutputStream file = new FileOutputStream(new File("C://Docs//"+Factura+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            PdfPTable tabla = new PdfPTable(3);
            Paragraph p = new Paragraph("Restaurante Las Delicias \n\n", FontFactory.getFont("Arial",16,Font.ITALIC,BaseColor.BLUE));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            p = new Paragraph("Factura No: "+Factura+" \n", FontFactory.getFont("Arial",12,Font.ITALIC,BaseColor.RED));
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            p = new Paragraph("A nombre de:  "+Nombre_Cliente+" "+Apellido_Cliente+" \n", FontFactory.getFont("Arial",12,Font.ITALIC,BaseColor.BLACK));
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            p = new Paragraph("Atendido Por:  "+Nombre_Empleado+" "+Apellido_Empleado+" \n", FontFactory.getFont("Arial",12,Font.ITALIC,BaseColor.BLACK));
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            p = new Paragraph("Atendido en la Mesa No.  "+No_Mesa+" La Cuenta ya ha sido: "+Estado+" \n\n", FontFactory.getFont("Arial",12,Font.ITALIC,BaseColor.BLACK));
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);
            document.add(new Paragraph(""));
            float[] mediaCeldas ={4.50f,4.50f,4.50f};
            tabla.setWidths(mediaCeldas);
            tabla.addCell(new Paragraph("Id Producto", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Producto", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Precio", FontFactory.getFont("Arial",12)));
        
                while (r.next()){
                tabla.addCell(new Paragraph(r.getString("Id_Producto"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Producto"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Precio"), FontFactory.getFont("Arial",10)));
                Total=Total+Integer.parseInt(r.getString("Precio"));
                }
            tabla.addCell(new Paragraph(" ", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Total", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph(String.valueOf(Total) , FontFactory.getFont("Arial",12)));
                
                document.add(tabla);              
                document.close();
                file.close(); 
                JOptionPane.showMessageDialog(null, "Se Ha pagado La Orden y generado la Factura \n No: "+Facts.CodPedido.getText()+"\nGuardada en C://Docs//"+CodPedido.getText()+".pdf");
                Facts fac = new Facts();
                fac.setVisible(false);
            }
            }
            catch (SQLException e)
            {
            }
    }
    
    //Codigo Para Enviar Factura Cancelada Por Correo
    public void EnviarCorreo() throws MessagingException
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port","587");
        props.setProperty("mail.smtp.user", "correoprogratest@gmail.com");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        BodyPart texto = new MimeBodyPart();
        texto.setText("Factura de Su Compra");
        BodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:/Docs/"+Facts.Orden_Env.getText()+".pdf")));
        adjunto.setFileName(Facts.Orden_Env.getText()+".pdf");
        MimeMultipart multiParte = new MimeMultipart();
        multiParte.addBodyPart(texto);
        multiParte.addBodyPart(adjunto);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("correoprogratest@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(Facts.Correo.getText()));
        message.setSubject("Factura Restaurante las Delicias");
        message.setContent(multiParte);
        Transport t = session.getTransport("smtp");
        t.connect("correoprogratest@gmail.com","20Correo20");
        t.sendMessage(message,message.getAllRecipients());
        t.close();

    }
    
    //Codigo Para Generar Registro de Mesas 
    public void GenerarPDFMesas() throws FileNotFoundException, DocumentException, IOException, SQLException
    {
        try
        {
            conn =conex.getConection();
            ps= (com.mysql.cj.jdbc.CallableStatement) conn.prepareCall("{Call BuscarFacturaMesa(?)}");
            ps.setString(1, FactsMesa.CodMesa.getText());
            String Mesa=FactsMesa.CodMesa.getText();
            r=ps.executeQuery();
            if (r.next())
            {
            OutputStream file = new FileOutputStream(new File("C://Docs//Mesa No "+Mesa+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            PdfPTable tabla = new PdfPTable(6);
            Paragraph p = new Paragraph("Registro de Mesa No "+Mesa+"\n\n", FontFactory.getFont("Arial",16,Font.ITALIC,BaseColor.BLUE));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph(""));
            float[] mediaCeldas ={3.50f,6.50f,6.50f,3.50f,4.50f,3.50f};
            tabla.setWidths(mediaCeldas);
            tabla.addCell(new Paragraph("Cod Pedido", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Nombre Cliente", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Nombre Empleado", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Id Producto", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Producto", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Precio", FontFactory.getFont("Arial",12)));
        
                while (r.next()){
                tabla.addCell(new Paragraph(r.getString("Codigo_Pedido"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Nombre_Cliente")+" "+r.getString("Apellido_Cliente"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Nombre_Empleado")+" "+r.getString("Apellido_Empleado"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Id_Producto"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Producto"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(r.getString("Precio"), FontFactory.getFont("Arial",10)));
                Total=Total+Integer.parseInt(r.getString("Precio"));
                }
            tabla.addCell(new Paragraph(" ", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph(" ", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph(" ", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph(" ", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Total", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph(String.valueOf(Total) , FontFactory.getFont("Arial",12)));
                
                document.add(tabla);              
                document.close();
                file.close();  
                JOptionPane.showMessageDialog(null, "Se Ha pagado La Orden y generado la Factura de la mesa \n No: "+CodMesa.getText()+"\nGuardada en C://Docs//Mesa No."+CodMesa.getText()+".pdf");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No se encontro la Mesa");
            }
            }
            catch (SQLException e)
            {
            }
    }

    //Codigo Para Enviar Registro de Mesas Por Correo
    public void EnviarCorreoMesa() throws MessagingException
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port","587");
        props.setProperty("mail.smtp.user", "correoprogratest@gmail.com");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        BodyPart texto = new MimeBodyPart();
        texto.setText("Registro de Mesa No "+FactsMesa.Mesa_Env.getText());
        BodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:/Docs/Mesa No "+FactsMesa.Mesa_Env.getText()+".pdf")));
        adjunto.setFileName("Mesa No "+FactsMesa.Mesa_Env.getText()+".pdf");
        MimeMultipart multiParte = new MimeMultipart();
        multiParte.addBodyPart(texto);
        multiParte.addBodyPart(adjunto);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("correoprogratest@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(FactsMesa.Correo.getText()));
        message.setSubject("Registro de Mesas");
        message.setContent(multiParte);
        Transport t = session.getTransport("smtp");
        t.connect("correoprogratest@gmail.com","20Correo20");
        t.sendMessage(message,message.getAllRecipients());
        t.close();
    }
}

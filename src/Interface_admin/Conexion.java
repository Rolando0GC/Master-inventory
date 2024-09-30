/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface_admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author SONY
 */
public class Conexion {
    Connection conexion;
    String   clave;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
    public void getConexion(){
   
        try {
            String url="jdbc:mysql://localhost:3306/"+"control_inventario";
            conexion=DriverManager.getConnection(url, "root", "root");
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error al conectar");
        }  
    } 
     public Vector datos(ResultSet rs, int nc, ResultSetMetaData md){
      Vector v=new Vector<>();
        try {
      
        
        while(rs.next()){
            Vector datos=new Vector();
            for(int i=1;i<=nc;i++){
                String tipo = md.getColumnTypeName(i);
                switch(tipo){
                    case "VARCHAR":
                        datos.add(rs.getString(i));
                        break;
                    case "float":
                        datos.add(rs.getFloat(i));
                        break;
                    case "double":
                        datos.add(rs.getDouble(i));
                        break;
                    case "INT":
                        datos.add(rs.getInt(i));
                        break;
                    default: System.out.println(" "+tipo);
                }
            }
            v.add(datos);
            
        }
    } catch (SQLException ex) {
        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
    }
    return v;
    }
     public void verTabla(String nomT,JScrollPane a){
         
        try {
            Vector titulos = new Vector();
            Vector filas =new Vector();
            
            ResultSet resultado;
            getConexion();
           
            
            String instruccion="select * from "+nomT;
            Statement sentencia=conexion.createStatement();
            resultado=sentencia.executeQuery(instruccion);
            
            ResultSetMetaData metaDatos=resultado.getMetaData();
        
            int numColumnas=metaDatos.getColumnCount();
            
            for(int i=1;i<=numColumnas;i++){
                titulos.add(metaDatos.getColumnName(i));
                
            }
            filas=(datos(resultado, numColumnas, metaDatos));
            JTable tabla=new JTable(filas,titulos);
            a.setViewportView(tabla);
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
 }
}

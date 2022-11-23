/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Categoria;
import Negocio.Condicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author
 */
public class CondicionDAO {
    Categoria categoriaSalida;
    Condicion condicionSalida;
    private ArrayList<Condicion> condiciones = new ArrayList();
    public CondicionDAO(){
        
    }

    public void incluirCondicion(Condicion condicion) throws RHException, SQLException{
        try {
        String strSQL = "INSERT INTO cond (idcondicion, descripcion, permitevarios) VALUES (?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setInt(1, condicion.getK_idCondicion()); 
        prepStmt.setString(2, condicion.getDescripcion()); 
        prepStmt.setInt(3, condicion.getPermiteVarios()); 
        //prepStmt.setDate(7, java.sql.Date.valueOf(condicion.getHire_date()));
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
          CaException.getInstance().setDetalle(e);
           //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
      public ArrayList<Condicion> buscarCondiciones() throws RHException {
        try {
            String strSQL = "SELECT c.idcategoria, c.nombre_categoria, c.puntaje, c.idcondicion, co.idcondicion, co.descripcion, co.permitevarios"
                    + " FROM CATEG c INNER JOIN COND co "
                    + " ON c.idcondicion = co.idcondicion";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            ResultSet rs = prepStmt.executeQuery();
            int idCondicionAnterior; 
            int idCondicion = 0;
            ArrayList<Categoria> categorias;
            while ((idCondicion!=0)||(rs.next())) {
                categorias = new ArrayList();
                idCondicionAnterior = rs.getInt(4);
                idCondicion = idCondicionAnterior;
                condicionSalida = new Condicion(rs.getInt(5), rs.getString(6), rs.getInt(7));
                do{                   
                    categorias.add(categoriaSalida =  new  Categoria (rs.getInt(1), rs.getString(2), rs.getInt(3), idCondicionAnterior));
                    if(rs.next()){
                        idCondicionAnterior = idCondicion;
                        idCondicion = rs.getInt(4);
                    }else{
                        idCondicion = 0;
                    }
                }while(idCondicionAnterior==idCondicion);
                condicionSalida.setListaCat(categorias);
                condiciones.add(condicionSalida);
            }
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
        return condiciones;
    }
}

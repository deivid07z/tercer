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
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author
 */
public class CategoriaDAO {
    private Categoria categoriaSalida;
  
    public CategoriaDAO(){
        
    }

    public void incluirCategoria(Categoria categoria, Condicion condicion) throws RHException, SQLException{
        try {
        String strSQL = "INSERT INTO categ (idcategoria, nombre_categoria, puntaje, idcondicion) VALUES (?,?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setInt(1, categoria.getK_idCategoria()); 
        prepStmt.setString(2, categoria.getNombreCategoria()); 
        prepStmt.setInt(3, categoria.getPuntaje()); 
        prepStmt.setInt(4, condicion.getK_idCondicion()); 
        //prepStmt.setDate(7, java.sql.Date.valueOf(categoria.getHire_date()));
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
    
    public void buscarCategoria(Categoria categoria) throws RHException {
        try {
            String strSQL = "SELECT idcategoria, nombre_categoria, puntaje, idcondicion FROM CATEG WHERE idcategoria = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setInt(1, categoria.getK_idCategoria());
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                categoriaSalida =  new  Categoria (rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
            }
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public Categoria buscarCategoria(int idCategoria, int idCondicion) throws RHException {
        try {
            String strSQL = "SELECT idcategoria, nombre_categoria, puntaje FROM CATEG WHERE idcategoria = ? AND idcondicion = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setInt(1, idCategoria);
            prepStmt.setInt(2, idCondicion);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                categoriaSalida =  new  Categoria (rs.getInt(1), rs.getString(2), rs.getInt(3), idCondicion);
            }
            return categoriaSalida;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
}

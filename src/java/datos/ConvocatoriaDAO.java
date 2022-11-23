/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Categoria;
import Negocio.Convocatoria;
import Negocio.Estado;
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
public class ConvocatoriaDAO {
    public ConvocatoriaDAO(){
        
    }
    Convocatoria convocatoria;
    public void incluirConvocatoria(Convocatoria convocatoria) throws RHException, SQLException{
        try {
        String strSQL = "INSERT INTO conv (periodo, fecha_apertura, fecha_cierre) VALUES (?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, convocatoria.getK_periodo()); 
        prepStmt.setDate(2, convocatoria.getFechaApertura());
        prepStmt.setDate(3, convocatoria.getFechaCierre());
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
    public Convocatoria buscarConvocatoria(java.sql.Date sqlDate) throws RHException {
        try {
            String strSQL = "SELECT periodo, fecha_apertura, fecha_cierre FROM CONV WHERE fecha_apertura <= ? and fecha_cierre >= ? ";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setDate(1, sqlDate);
            prepStmt.setDate(2, sqlDate);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                convocatoria =  new  Convocatoria (rs.getString(1), rs.getDate(2), rs.getDate(3));
            }
            return convocatoria;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public Convocatoria buscarConvocatoriaPK(String periodo) throws RHException {
        try {
            String strSQL = "SELECT periodo, fecha_apertura, fecha_cierre FROM CONV WHERE periodo = ? ";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                convocatoria =  new  Convocatoria (rs.getString(1), rs.getDate(2), rs.getDate(3));
            }
            return convocatoria;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void actualizarConvocatoria(Convocatoria convocatoria) throws RHException {
        try {
            String strSQL = "UPDATE CONV SET fecha_apertura = ?, fecha_cierre = ?"
                    + " WHERE periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setDate(1,convocatoria.getFechaApertura());
            prepStmt.setDate(2,convocatoria.getFechaCierre());
            prepStmt.setString(3,convocatoria.getK_periodo());
            prepStmt.executeUpdate();
            prepStmt.close();
            ServiceLocator.getInstance().commit();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
}

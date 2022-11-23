/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Revisor;
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
public class RevisorDAO {
    private Revisor revisorSalida;
    
    public RevisorDAO(){
        
    }
    
    public void incluirRevisor(Revisor revisor) throws RHException, SQLException{
        try {
      
        String strSQL = "INSERT INTO REV (cod_revisor, nombre_revisor) VALUES (?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1, revisor.getK_codRevisor()); 
        prepStmt.setString(2, revisor.getNombreRevisor()); 
        //prepStmt.setDate(7, java.sql.Date.valueOf(revisor.getHire_date()));
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
          CaException.getInstance().setDetalle(e);
         //  throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
    
    public Revisor buscarRevisor(Long codigo_revisor) throws RHException {
        try {
            String strSQL = "SELECT cod_revisor, nombre_revisor FROM REV WHERE cod_revisor = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigo_revisor);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                revisorSalida = new Revisor(rs.getLong(1), rs.getString(2));
            }
            return revisorSalida;
        } catch (SQLException e) {
           CaException.getInstance().setDetalle(e);
            //throw new RHException("RevisorDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
        return null;
    }
}

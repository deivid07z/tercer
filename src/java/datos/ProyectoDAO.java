/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Facultad;
import Negocio.Proyecto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author
 */
public class ProyectoDAO {
    public ProyectoDAO(){
        
    }
    
    public void incluirProyecto(Proyecto proyecto, Facultad facultad) throws RHException, SQLException{
        try {
      
        String strSQL = "INSERT INTO proy (idproyecto, nombre_proyecto, idfacultad) VALUES (?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, proyecto.getK_idProyecto()); 
        prepStmt.setString(2, proyecto.getNombreProyecto()); 
        prepStmt.setString(3, facultad.getNombreFacultad());   
        //prepStmt.setDate(7, java.sql.Date.valueOf(proyecto.getHire_date()));
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
          CaException.getInstance().setDetalle(e);
     //      throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
}

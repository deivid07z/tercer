/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Estudiante;
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
public class EstudianteDAO {
    private Estudiante estudianteSalida;
  
    public EstudianteDAO(){
    }
    
   
    public void incluirEstudiante(Estudiante estudiante) throws RHException, SQLException{
        try {
      
        String strSQL = "INSERT INTO est (codigo, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, estadoestudiante, idproyecto, indicematricula, correoestudiante) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1,estudiante.getK_codigo()); 
        prepStmt.setString(2, estudiante.getPrimerNombre()); 
        prepStmt.setString(3, estudiante.getSegundoNombre()); 
        prepStmt.setString(4, estudiante.getPrimerApellido()); 
        prepStmt.setString(5, estudiante.getSegundoApellido());
        prepStmt.setString(6, estudiante.getEstadoEstudiante());
        prepStmt.setString(7, estudiante.getFk_idProyecto()); 
        prepStmt.setFloat(8, estudiante.getIndiceMatricula()); 
        prepStmt.setString(9, estudiante.getCorreoEst()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
          CaException.getInstance().setDetalle(e);
          // throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
    public Estudiante buscarEstudiante(Long codigoEst) throws RHException {
        try {
            String strSQL = "SELECT codigo, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, estadoestudiante, idproyecto, indicematricula, correoestudiante FROM est WHERE codigo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigoEst);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                estudianteSalida = new Estudiante(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getFloat(8), rs.getString(9));
            }
            return estudianteSalida;
            
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
        return null;
    }
}

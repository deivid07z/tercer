/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Estudiante;
import Negocio.Retiro;
import Negocio.Tipo;
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
public class RetiroDAO {
    public RetiroDAO(){
        
    }
    
    public void incluirRetiro(Retiro retiro, Estudiante estudiante, Tipo tipo) throws RHException, SQLException{
        try {
      
        String strSQL = "INSERT INTO RET (desc_ret, fecha_ret, idtipo, codigo, periodo) VALUES (?,?,?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, retiro.getDescRet()); 
        prepStmt.setDate(2, retiro.getFechaRet()); 
        prepStmt.setString(3, tipo.getK_idTipo());   
        prepStmt.setLong(4, estudiante.getK_codigo());   
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
          CaException.getInstance().setDetalle(e);
        //   throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
}

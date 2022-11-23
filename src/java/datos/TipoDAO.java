/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Convocatoria;
import Negocio.Estado;
import Negocio.Tipo;
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
public class TipoDAO {
    public TipoDAO(){
        
    }
    Tipo tipo;
    public void incluirTipo(Tipo tipo) throws RHException, SQLException{
        try {
        String strSQL = "INSERT INTO TIP (idtipo, cantidad, periodo) VALUES (?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, tipo.getK_idTipo()); 
        prepStmt.setInt(2, tipo.getCantidad());
        prepStmt.setString(3, tipo.getPfk_periodo());
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
      } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
          //  throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
        
     public ArrayList<Tipo> buscarTipoPK(String periodo) throws RHException, SQLException{
        try {
        String strSQL = "select * from TIP where periodo = ? order by idTipo";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, periodo); 
        ResultSet rs = prepStmt.executeQuery();
        ArrayList<Tipo> tipos =  new ArrayList();
        while (rs.next()) {
            tipos.add(tipo =  new  Tipo(rs.getString(1), rs.getInt(2), rs.getString(3)));
        }
        return tipos;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void actualizarTipo(Tipo tipo) throws RHException {
        try {
            String strSQL = "UPDATE TIP SET cantidad = ?"
                    + " WHERE idtipo = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setInt(1,tipo.getCantidad());
            prepStmt.setString(2,tipo.getK_idTipo());
            prepStmt.setString(3,tipo.getPfk_periodo());
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


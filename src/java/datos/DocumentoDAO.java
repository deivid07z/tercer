/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Documento;
import Negocio.Solicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class DocumentoDAO {

    public void incluirDocumento(Documento doc, Solicitud solicitud) throws RHException, SQLException {
        try {
            String strSQL = "INSERT INTO DOC (documento,idcondicion, idcategoria, periodo, codigo, estado) VALUES (?,?,?,?,?,default)";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, doc.getDocumento());
            prepStmt.setInt(2, doc.getFk_idCondicion());
            prepStmt.setInt(3, doc.getPfk_idCategoria());
            prepStmt.setString(4, solicitud.getPfk_periodo());
            prepStmt.setLong(5, solicitud.getPfk_codigo());
            prepStmt.executeUpdate();
            ServiceLocator.getInstance().commit();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void actualizarDocumento(Documento doc) throws RHException, SQLException {
        try {
            String strSQL = "UPDATE DOC SET COD_REVISOR=?, ESTADO=? WHERE codigo=? and periodo=? and idcategoria=? and idcondicion=?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, doc.getFk_codRevisor());
            prepStmt.setString(2, doc.getEstado());
            prepStmt.setLong(3, doc.getPfk_codigo());
            prepStmt.setString(4, doc.getPfk_periodo());
            prepStmt.setInt(5, doc.getPfk_idCategoria());
            prepStmt.setInt(6, doc.getFk_idCondicion());
            prepStmt.executeUpdate();
            ServiceLocator.getInstance().commit();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
}

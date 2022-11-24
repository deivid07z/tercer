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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class SolicitudDAO {

    private Solicitud solicitud;

    public void incluirSolicitud(Solicitud solicitud) throws RHException, SQLException {
        PreparedStatement prepStmt;
        try {
            String strSQL = "INSERT INTO SOL (codigo,estado,puntaje, fecha_solicitud, idestado, periodo) VALUES (?,default,default,default,?,?)";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, solicitud.getPfk_codigo());
            prepStmt.setInt(2, solicitud.getFk_idEstado());
            prepStmt.setString(3, solicitud.getPfk_periodo());
            prepStmt.executeUpdate();
            ServiceLocator.getInstance().commit();
            ServiceLocator.getInstance().liberarConexion();
            Documento doc;
            DocumentoDAO docDAO = new DocumentoDAO();
            for (int i = 0; i < solicitud.getListDocumento().size(); i++) {
                doc = solicitud.getListDocumento().get(i);
                docDAO.incluirDocumento(doc, solicitud);
            }
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            
        }finally{
            prepStmt.close();
        }
    }

    public ArrayList<String> buscarSolicitud(Long codigo, String periodo) throws RHException {
        PreparedStatement prepStmt;
        try {
            ArrayList<String> codes = new ArrayList<String>();
            String strSQL = "SELECT codigo FROM sol WHERE periodo = ? and estado = 'CK'";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                codes.add(rs.getString(1));
            }
            return codes;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
            prepStmt.close();
        }
        return null;
    }

    public ArrayList<String> listarEstudianteCk(String periodo) throws RHException {
        PreparedStatement prepStmt;
        try {
            ArrayList<String> codes = new ArrayList<String>();
            String strSQL = "SELECT codigo FROM sol WHERE periodo = ? and estado = 'CK'";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                codes.add(rs.getString(1));
            }
            return codes;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
            prepStmt.close();
        }
        return null;
    }

    public void actualizarSolicitud(Solicitud solicitud) throws RHException, SQLException {
        PreparedStatement prepStmt;
        try {
            String strSQL = "UPDATE SOL SET estado=? WHERE codigo = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, solicitud.getEstado());
            prepStmt.setLong(2, solicitud.getPfk_codigo());
            prepStmt.setString(3, solicitud.getPfk_periodo());
            prepStmt.executeUpdate();
            ServiceLocator.getInstance().commit();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
            prepStmt.close();
        }
    }
    
    public void eliminarSolicitud(Long codigo, String periodo) throws RHException, SQLException {
        PreparedStatement prepStmt;
        try {
            String strSQL = "DELETE FROM SOL WHERE codigo = ? AND periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigo);
            prepStmt.setString(2, periodo);
            prepStmt.executeUpdate();
            prepStmt.close();
            ServiceLocator.getInstance().commit();
            ServiceLocator.getInstance().liberarConexion();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        }finally{
            prepStmt.close();
        }
    }
}

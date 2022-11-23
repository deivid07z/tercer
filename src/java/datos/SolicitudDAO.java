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
        try {
            String strSQL = "INSERT INTO SOL (codigo,estado,puntaje, fecha_solicitud, idestado, periodo) VALUES (?,default,default,default,?,?)";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, solicitud.getPfk_codigo());
            prepStmt.setInt(2, solicitud.getFk_idEstado());
            prepStmt.setString(3, solicitud.getPfk_periodo());
            prepStmt.executeUpdate();
            prepStmt.close();
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
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        }
    }

    public Solicitud buscarSolicitud(Long codigo, String periodo) throws RHException {
        try {
            String strSQL = "SELECT * FROM DOC WHERE codigo = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigo);
            prepStmt.setString(2, periodo);
            ResultSet rs = prepStmt.executeQuery();
            Documento doc;
            ArrayList<Documento> lsDoc = new ArrayList<Documento>();
            while (rs.next()) {
                doc = new Documento(rs.getString(1), rs.getInt(2), rs.getLong(3), rs.getInt(4), rs.getString(5), rs.getLong(6), rs.getString(7));
                lsDoc.add(doc);
            }
            strSQL = "SELECT * FROM SOL WHERE codigo = ? and periodo = ?";
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigo);
            prepStmt.setString(2, periodo);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                solicitud = new Solicitud(rs.getLong(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getInt(5), rs.getString(6), lsDoc);
            }
            prepStmt.close();
            return solicitud;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("RevisorDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
        return null;
    }

    public ArrayList<String> listarEstudianteCk(String periodo) throws RHException {
        try {
            ArrayList<String> codes = new ArrayList<String>();
            String strSQL = "SELECT codigo FROM sol WHERE periodo = ? and estado = 'CK'";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
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
        }
        return null;
    }

    public void actualizarSolicitud(Solicitud solicitud) throws RHException, SQLException {
        try {
            String strSQL = "UPDATE SOL SET estado=? WHERE codigo = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
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
        }
    }
    
    public void eliminarSolicitud(Long codigo, String periodo) throws RHException, SQLException {
        try {
            String strSQL = "DELETE FROM SOL WHERE codigo = ? AND periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigo);
            prepStmt.setString(2, periodo);
            prepStmt.executeUpdate();
            prepStmt.close();
            ServiceLocator.getInstance().commit();
            ServiceLocator.getInstance().liberarConexion();
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
        }
    }
}

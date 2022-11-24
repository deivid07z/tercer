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
import java.util.ArrayList;
import util.CaException;
import util.ServiceLocator;

/**
 *
 * @author danie
 */
public class BeneficiarioDAO {
    private long puntajeSolicitud;
    public BeneficiarioDAO(){
    
    }
    public long puntajeSolicitud(Long codigoEst, String periodo) throws SQLException{
        Connection conexion;
        PreparedStatement prepStmt;
        try {
            String strSQL = "SELECT puntaje FROM sol WHERE codigo = ? and periodo = ?";
            conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigoEst);
            prepStmt.setString(2, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                puntajeSolicitud = rs.getLong(1);
            }
            return puntajeSolicitud;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        }  finally {
            prepStmt.close();
            ServiceLocator.getInstance().liberarConexion();
            conexion.close();
            
        }
        return 0;
    }
    public boolean beneficiario(Long codigoEst, String periodo) throws SQLException{
        PreparedStatement prepStmt;
        try {
            String strSQL = "SELECT * FROM ben WHERE codigo = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigoEst);
            prepStmt.setString(2, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
            prepStmt.close();
        }
        return false;
    }
    public ArrayList puntajeDocumento(Long codigoEst, String periodo) throws SQLException{
        ArrayList puntaje = new ArrayList();
        PreparedStatement prepStmt;
        try {
            String strSQL = "select puntaje from categ join doc using (idcategoria,idcondicion) "
                    + "where codigo=? and periodo=?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, codigoEst);
            prepStmt.setString(2, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                puntaje.add(rs.getLong(1));
            }
            return puntaje;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        }  finally {
            prepStmt.close();
            ServiceLocator.getInstance().liberarConexion();
        }
        return puntaje;
    }
    public String listarBeneficiarios(String periodo) throws SQLException{
        String correo="";
        PreparedStatement prepStmt;
        try {
            String strSQL = "SELECT correoestudiante FROM est JOIN ben USING (CODIGO) WHERE periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, periodo);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                correo=correo+rs.getString(1)+",";
            }
            correo= correo.substring(0, correo.length()-1);
            return correo;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
            prepStmt.close();
            
        }
        return correo;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Condicion;
import Negocio.Convocatoria;
import Negocio.Estado;
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
 * @author Camilo Enrique
 */
public class EstadoDAO {
    public EstadoDAO(){
        
    }
    private ArrayList<Estado> estados = new ArrayList();
    Estado estado;
    public void incluirEstado(Estado estado) throws RHException, SQLException{
        try {
        String strSQL = "INSERT INTO ESTADO (fecha_inicial, fecha_final, idestado, descripcion_estado, periodo) VALUES (?,?,?,?,?)";
        Connection conexion = ServiceLocator.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setDate(1, estado.getFecha_inicial()); 
        prepStmt.setDate(2, estado.getFecha_final());
        prepStmt.setInt(3, estado.getIdestado());
        prepStmt.setString(4, estado.getDescripcion_estado());
        prepStmt.setString(5, estado.getPeriodo());        
        System.out.println("guarde");
        prepStmt.executeUpdate();
        prepStmt.close();
        ServiceLocator.getInstance().commit();
        System.out.println("COMMIT");
      } catch (SQLException e) {
          //CaException.getInstance().setDetalle(e);
            System.out.println(e);
           //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
      }  finally {
         ServiceLocator.getInstance().liberarConexion();
      }
    }
    public Estado buscarEstado(java.sql.Date sqlDate) throws RHException {
        try {
           String strSQL = "SELECT fecha_inicial, fecha_final, idestado, descripcion_estado, periodo FROM ESTADO WHERE fecha_inicial <= ? and fecha_final >= ? ";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setDate(1, sqlDate);
            prepStmt.setDate(2, sqlDate);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                estado =  new  Estado(rs.getDate(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            }
            return estado;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public ArrayList<Estado> buscarEstadoPK(String periodo) throws RHException {
        try {
           String strSQL = "SELECT fecha_inicial, fecha_final, idestado, descripcion_estado, periodo FROM ESTADO WHERE periodo = ? order by idestado";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, periodo);
            ResultSet rs = prepStmt.executeQuery();
            ArrayList<Estado> estados = new ArrayList();
            while (rs.next()) {
                estados.add(estado =  new  Estado(rs.getDate(1), rs.getDate(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
            }
            return estados;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            throw new RHException("ApartamentoDAO", "No pudo recuperar el Apartmaento " + e.getMessage());
        }  finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void actualizarEstado(Estado estado) throws RHException {
        try {
            String strSQL = "UPDATE ESTADO SET fecha_inicial = ?, fecha_final = ?, descripcion_estado = ?"
                    + " WHERE idestado = ? and periodo = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setDate(1,estado.getFecha_inicial());
            prepStmt.setDate(2,estado.getFecha_final());
            prepStmt.setString(3,estado.getDescripcion_estado());
            prepStmt.setInt(4,estado.getIdestado());
            prepStmt.setString(5,estado.getPeriodo());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class RegistroDAO {
    private String nuevoUser;
    private String nuevoPassword;

    public RegistroDAO() {
    }
    
    public void registrar(String nuevoUser, String nuevoPassword, String tipo) throws RHException, SQLException{
        this.nuevoPassword = nuevoPassword;
        this.nuevoUser = nuevoUser;
        System.out.println("estoy en registrar");
        conectarCreador();
        crearUsuario();
        if (tipo.equals("Estudiante")) {
            asignarRolEstudiante();
        }
        if (tipo.equals("Revisor")) {
            asignarRolRevisor();
        }
        conectarUsuario();
    }
    
    public Boolean buscarUsuario(Long codigo) throws RHException, SQLException {
        try {
            conectarCreador();
            String strSQL = "SELECT USERNAME FROM DBA_USERS WHERE USERNAME = ?";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, "U_"+codigo);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            return false;
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void conectarCreador(){
        try {
           ServiceLocator.newInstance();
           ServiceLocator service = ServiceLocator.getInstance();
           service.CreateConnection("administrador", "admin");
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
        } finally {
        }
    }
    
    public Boolean conectarUsuario(){
        try {
           ServiceLocator.newInstance();
           ServiceLocator service = ServiceLocator.getInstance();
           service.CreateConnection("U_"+nuevoUser, nuevoPassword);
           return true;
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
            return false;
        } finally {
        }
    }
    
    public Boolean conectarUsuario(String nuevoUser, String nuevoPassword){
        try {
           ServiceLocator.newInstance();
           ServiceLocator service = ServiceLocator.getInstance();
           service.CreateConnection("U_"+nuevoUser, nuevoPassword);
           return true;
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
            return false;
        } finally {
        }
    }
    
    public void crearUsuario(){
        try {
           String strSQL; //= "alter session set \"_ORACLE_SCRIPT\"=true";
           Connection conexion = ServiceLocator.getInstance().tomarConexion();
           PreparedStatement prepStmt; //= conexion.prepareStatement(strSQL);
           //prepStmt.execute();
           strSQL = "CREATE USER U_"+nuevoUser+" IDENTIFIED BY "+nuevoPassword+
                   " DEFAULT TABLESPACE SAAUSR TEMPORARY TABLESPACE SAAUSRTMP QUOTA 2K ON SAADEF";
           prepStmt = conexion.prepareStatement(strSQL);
           prepStmt.execute();          
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void asignarRolEstudiante(){
        try {
           String strSQL = "GRANT R_ESTUDIANTE,R_CONSULTA,CONNECT TO U_"+nuevoUser;
           Connection conexion = ServiceLocator.getInstance().tomarConexion();
           PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
           prepStmt.execute();          
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
    
    public void asignarRolRevisor(){
        try {
           String strSQL = "GRANT R_REVISOR,R_CONSULTA,CONNECT TO U_"+nuevoUser;
           Connection conexion = ServiceLocator.getInstance().tomarConexion();
           PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
           prepStmt.execute();          
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
    }
}

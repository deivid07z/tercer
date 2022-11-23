/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import Negocio.Facultad;
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
public class FacultadDAO {
 
    ArrayList<Facultad> listFac = new ArrayList<Facultad>();
    
    public FacultadDAO(){
        
    }
    
    public ArrayList<Facultad> listarFacultades() throws RHException {
        try {
            String strSQL = "SELECT idfacultad,cupo,nombre_facultad FROM fac ";
            Connection conexion = ServiceLocator.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                listFac.add(new Facultad(rs.getString(1),rs.getInt(2),rs.getString(3)));
            }
            return listFac;
        } catch (SQLException e) {
            CaException.getInstance().setDetalle(e);
            //throw new RHException("EstudianteDAP", "No pudo recuperar el Estudiante " + e.getMessage());
        } finally {
            ServiceLocator.getInstance().liberarConexion();
        }
        return null;
    }
}

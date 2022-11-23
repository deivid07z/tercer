/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import datos.EstudianteDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CaException;
import util.RHException;

/**
 *
 * @author Sebastian
 */
public class CargarBaseDatos extends CargarEstudiantes{

    public Estudiante cargarEstudiante(Estudiante estud) {
        try {
            EstudianteDAO estudDao = new EstudianteDAO();
            return estudDao.buscarEstudiante(estud.getK_codigo());
        } catch (RHException ex) {
            CaException.getInstance().setDetalle(ex);
            return null;
        }
    }
    
}

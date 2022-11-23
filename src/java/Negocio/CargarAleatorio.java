/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import datos.EstudianteDAO;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CaException;
import util.RHException;

/**
 *
 * @author Sebastian
 */
public class CargarAleatorio extends CargarEstudiantes{

    public Estudiante cargarEstudiante(Estudiante estud) {
        try {
            EstudianteDAO estudDao = new EstudianteDAO();
            Random rd = new Random();
            estudDao.incluirEstudiante(new Estudiante(estud.getK_codigo(), "Pepito"+rd.nextInt(50)+1, "Pepe"+rd.nextInt(50)+1,
                    "Perez"+rd.nextInt(50)+1, "Paez"+rd.nextInt(50)+1, "MA", "1", rd.nextInt(50), "scva9708@gmail.com"));
            return estudDao.buscarEstudiante(estud.getK_codigo());
        } catch (RHException ex) {
            CaException.getInstance().setDetalle(ex);
        } catch (SQLException ex) {
            CaException.getInstance().setDetalle(ex);
        }
        return null;
    }
    
}

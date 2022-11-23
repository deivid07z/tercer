/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import datos.RevisorDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CaException;
import util.RHException;


/**
 *
 * @author Sebastian
 */
public class CargarBaseDatosRevisor extends CargarRevisor{

    public Revisor cargarRevisor(Revisor revis) {
        try {
            RevisorDAO revisorDao = new RevisorDAO();
            return revisorDao.buscarRevisor(revis.getK_codRevisor());
        } catch (RHException ex) {
            CaException.getInstance().setDetalle(ex);
            return null;
        }
    }

}

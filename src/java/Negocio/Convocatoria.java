/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import java.sql.Date;
/**
 *
 * @author Camilo Enrique
 */
public class Convocatoria {
    private String k_periodo;
    private Date fechaApertura;
    private Date fechaCierre;

    public Convocatoria(String k_periodo, Date fechaApertura, Date fechaCierre) {
        this.k_periodo = k_periodo;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
    }

    public Convocatoria(String k_periodo) {
        this.k_periodo = k_periodo;
    }

    public String getK_periodo() {
        return k_periodo;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

}


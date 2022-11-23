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
public class EstudianteFacultad {
    private String estadoAlmuerzo;
    private Date k_fecha;
    private String pfk_idFacultad;
    private long pfk_codigo;

    public EstudianteFacultad(String estadoAlmuerzo, Date k_fecha, Facultad fac, Estudiante estud) {
        this.estadoAlmuerzo = estadoAlmuerzo;
        this.k_fecha = k_fecha;
        this.pfk_idFacultad = fac.getK_idFacultad();
        this.pfk_codigo = estud.getK_codigo();
    }

    public String getEstadoAlmuerzo() {
        return estadoAlmuerzo;
    }

    public void setEstadoAlmuerzo(String estadoAlmuerzo) {
        this.estadoAlmuerzo = estadoAlmuerzo;
    }

    public Date getK_fecha() {
        return k_fecha;
    }

    public void setK_fecha(Date k_fecha) {
        this.k_fecha = k_fecha;
    }

    public String getPfk_idFacultad() {
        return pfk_idFacultad;
    }

    public void setPfk_idFacultad(String pfk_idFacultad) {
        this.pfk_idFacultad = pfk_idFacultad;
    }

    public long getPfk_codigo() {
        return pfk_codigo;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Camilo Enrique
 */
public class Proyecto {
    private String k_idProyecto ;
    private String nombreProyecto;
    private String fk_idFacultad;

    public Proyecto(String k_idProyecto, String nombreProyecto, String fk_idFacultad) {
        this.k_idProyecto = k_idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.fk_idFacultad = fk_idFacultad;
    }

    public Proyecto(String k_idProyecto) {
        this.k_idProyecto = k_idProyecto;
    }

    public String getK_idProyecto() {
        return k_idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public String getFk_idFacultad() {
        return fk_idFacultad;
    }

}

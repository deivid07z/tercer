/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.util.ArrayList;

/**
 *
 * @author Camilo Enrique
 */
public class Facultad {
    private String k_idFacultad;
    private int cupo;
    private String nombreFacultad;
    private ArrayList<Proyecto> proyectos;
    private Proyecto proy;
    
    public Facultad(String k_idFacultad, int cupo, String nombreFacultad) {
        this.k_idFacultad = k_idFacultad;
        this.cupo = cupo;
        this.nombreFacultad = nombreFacultad;
        proyectos = new ArrayList<Proyecto>();
    }

    public Facultad(String k_idFacultad) {
        this.k_idFacultad = k_idFacultad;
    }
    
    public void crearProyecto(String k_idProyecto, String nombreProyecto){
        proy = new Proyecto(k_idProyecto, nombreProyecto, k_idFacultad);
        proyectos.add(proy);
    }

    public String getK_idFacultad() {
        return k_idFacultad;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }
    
}

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
public class Estudiante {
    private long k_codigo;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String estadoEstudiante;
    private String fk_idProyecto;
    private float indiceMatricula;    
    private String correoEst;
    

    public long getK_codigo() {
        return k_codigo;
    }

    public void setK_codigo(long k_codigo) {
        this.k_codigo = k_codigo;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEstadoEstudiante() {
        return estadoEstudiante;
    }

    public void setEstadoEstudiante(String estadoEstudiante) {
        this.estadoEstudiante = estadoEstudiante;
    }

    public String getFk_idProyecto() {
        return fk_idProyecto;
    }

    public void setFk_idProyecto(String fk_idProyecto) {
        this.fk_idProyecto = fk_idProyecto;
    }

    public float getIndiceMatricula() {
        return indiceMatricula;
    }

    public void setIndiceMatricula(float indiceMatricula) {
        this.indiceMatricula = indiceMatricula;
    }

    public String getCorreoEst() {
        return correoEst;
    }

    public void setCorreoEst(String correoEst) {
        this.correoEst = correoEst;
    } 
    
    
    
    public Estudiante(long k_codigo, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String estadoEstudiante, String fk_idProyecto, float indiceMatricula, String correoEst) {
        this.k_codigo = k_codigo;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.estadoEstudiante = estadoEstudiante;
        this.fk_idProyecto = fk_idProyecto;
        this.indiceMatricula = indiceMatricula;
        this.correoEst = correoEst;
    }

    public Estudiante(long k_codigo) {
        this.k_codigo = k_codigo;
    }
    
}

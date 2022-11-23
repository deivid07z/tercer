/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Sebastian Vanegas
 */
public class Documento {
    private String documento;
    private int fk_idCondicion;
    private long fk_codRevisor;
    private int pfk_idCategoria;
    private String pfk_periodo;
    private long pfk_codigo;
    private String estado;
    private Integer puntaje;

    public Documento(String documento, int fk_idCondicion, int pfk_idCategoria, String pfk_periodo, long pfk_codigo) {
        this.documento = documento;
        this.fk_idCondicion = fk_idCondicion;
        this.pfk_idCategoria = pfk_idCategoria;
        this.pfk_periodo = pfk_periodo;
        this.pfk_codigo = pfk_codigo;
    }

    public Documento(String documento, int fk_idCondicion, long fk_codRevisor, int pfk_idCategoria, String pfk_periodo, long pfk_codigo, String estado) {
        this.documento = documento;
        this.fk_idCondicion = fk_idCondicion;
        this.fk_codRevisor = fk_codRevisor;
        this.pfk_idCategoria = pfk_idCategoria;
        this.pfk_periodo = pfk_periodo;
        this.pfk_codigo = pfk_codigo;
        this.estado = estado;
    }

    public Documento(String documento, int fk_idCondicion, long fk_codRevisor, int pfk_idCategoria, String pfk_periodo, long pfk_codigo, String estado, Integer puntaje) {
        this.documento = documento;
        this.fk_idCondicion = fk_idCondicion;
        this.fk_codRevisor = fk_codRevisor;
        this.pfk_idCategoria = pfk_idCategoria;
        this.pfk_periodo = pfk_periodo;
        this.pfk_codigo = pfk_codigo;
        this.estado = estado;
        this.puntaje = puntaje;
    }

    public String getDocumento() {
        return documento;
    }

    public int getFk_idCondicion() {
        return fk_idCondicion;
    }

    public long getFk_codRevisor() {
        return fk_codRevisor;
    }

    public int getPfk_idCategoria() {
        return pfk_idCategoria;
    }

    public String getPfk_periodo() {
        return pfk_periodo;
    }

    public long getPfk_codigo() {
        return pfk_codigo;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setFk_codRevisor(long fk_codRevisor) {
        this.fk_codRevisor = fk_codRevisor;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

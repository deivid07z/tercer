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
public class Estado {

    private Date fecha_inicial;
    private Date fecha_final;
    private int idestado;
    private String descripcion_estado;
    private String periodo;

    public Estado(Date fecha_inicial, Date fecha_final, int idestado, String descripcion_estado, String periodo) {
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.idestado = idestado;
        this.descripcion_estado = descripcion_estado;
        this.periodo = periodo;
    }

    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public String getDescripcion_estado() {
        return descripcion_estado;
    }

    public void setDescripcion_estado(String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    
    
    
}

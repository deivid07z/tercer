/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Solicitud {
    private long pfk_codigo;
    private String estado;
    private int puntaje;
    private Date fecha_solicitud;
    private int fk_idEstado;
    private String pfk_periodo;
    private ArrayList<Documento> listDocumento;

    public Solicitud(long pfk_codigo, int fk_idEstado, String pfk_periodo, ArrayList<Documento> listDocumento) {
        this.pfk_codigo = pfk_codigo;
        this.fk_idEstado = fk_idEstado;
        this.pfk_periodo = pfk_periodo;
        this.listDocumento = listDocumento;
    }

    public Solicitud(long pfk_codigo, String estado, int puntaje, Date fecha_solicitud, int fk_idEstado, String pfk_periodo, ArrayList<Documento> listDocumento) {
        this.pfk_codigo = pfk_codigo;
        this.estado = estado;
        this.puntaje = puntaje;
        this.fecha_solicitud = fecha_solicitud;
        this.fk_idEstado = fk_idEstado;
        this.pfk_periodo = pfk_periodo;
        this.listDocumento = listDocumento;
    }

    public long getPfk_codigo() {
        return pfk_codigo;
    }

    public String getEstado() {
        return estado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public int getFk_idEstado() {
        return fk_idEstado;
    }

    public String getPfk_periodo() {
        return pfk_periodo;
    }

    public ArrayList<Documento> getListDocumento() {
        return listDocumento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

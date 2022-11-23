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
public class Retiro {
    private String descRet;
    private Date fechaRet;
    private String fk_idTipo;
    private long pfk_codigo;
    private String pfk_periodo;

    public Retiro(String descRet, Date fechaRet) {
        this.descRet = descRet;
        this.fechaRet = fechaRet;
    }

    public String getDescRet() {
        return descRet;
    }

    public Date getFechaRet() {
        return fechaRet;
    }

    public String getFk_idTipo() {
        return fk_idTipo;
    }

    public long getPfk_codigo() {
        return pfk_codigo;
    }

    public String getPfk_periodo() {
        return pfk_periodo;
    }
    
}

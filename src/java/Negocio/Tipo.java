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
public class Tipo {
    private String k_idTipo;
    private int cantidad;
    private String pfk_periodo;

    public Tipo(String k_idTipo, int cantidad, String pfk_periodo) {
        this.k_idTipo = k_idTipo;
        this.cantidad = cantidad;
        this.pfk_periodo = pfk_periodo;
    }

    public String getK_idTipo() {
        return k_idTipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getPfk_periodo() {
        return pfk_periodo;
    }
    
}

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
public class Categoria {
    private int k_idCategoria;
    private String nombreCategoria;
    private int puntaje;
    private int pfk_idCondicion;

    public Categoria(int k_idCategoria, String nombreCategoria, int puntaje, int pfk_idCondicion) {
        this.k_idCategoria = k_idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.puntaje = puntaje;
        this.pfk_idCondicion = pfk_idCondicion;
    }
    
    public int getK_idCategoria() {//Revisar Posteriormente si se elimina
        return k_idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPfk_idCondicion() {
        return pfk_idCondicion;
    }
}

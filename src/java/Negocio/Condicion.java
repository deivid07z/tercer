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
public class Condicion {
    private int k_idCondicion;
    private String descripcion;
    private int permiteVarios;
    private Categoria cat;
    private ArrayList<Categoria> listaCat;

    public Condicion(int k_idCondicion, String descripcion, int permiteVarios) {
        this.k_idCondicion = k_idCondicion;
        this.descripcion = descripcion;
        this.permiteVarios = permiteVarios;
        listaCat = new ArrayList<Categoria>();
    }
    
    public void crearCategoria(int k_idCategoria, String nombreCategoria, int puntaje){
        cat = new Categoria(k_idCategoria, nombreCategoria, puntaje, k_idCondicion);
        listaCat.add(cat);
    }

    public int getK_idCondicion() {
        return k_idCondicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPermiteVarios() {
        return permiteVarios;
    }

    public ArrayList<Categoria> getListaCat() {
        return listaCat;
    }

    public void setListaCat(ArrayList<Categoria> listaCat) {
        this.listaCat = listaCat;
    }
    
}

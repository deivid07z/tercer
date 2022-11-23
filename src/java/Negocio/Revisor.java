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
public class Revisor {
    private long k_codRevisor;
    private String nombreRevisor;
    

    public Revisor(long k_codRevisor, String nombreRevisor) {
        this.k_codRevisor = k_codRevisor;
        this.nombreRevisor = nombreRevisor;
    }

    public String getNombreRevisor() {
        return nombreRevisor;
    }

    public void setNombreRevisor(String nombreRevisor) {
        this.nombreRevisor = nombreRevisor;
    }

    public Revisor(long k_codRevisor) {
        this.k_codRevisor = k_codRevisor;
    }
    
    public long getK_codRevisor() {
        return k_codRevisor;
    }

    
}

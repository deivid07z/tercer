/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author user
 */
public class Ingreso {
    
    private String user;
    private String password;

    public Ingreso(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public Boolean login(){
        try {
           ServiceLocator.newInstance();
           ServiceLocator service = ServiceLocator.getInstance();
           service.CreateConnection(user,password);
           return true;
        } catch (Exception e) {
            CaException.getInstance().setDetalle(e);
            return false;
        }
    }

    public Boolean conectar(String user, String password) {
        this.user = user;
        this.password = password;
        return login();
    }
    
}

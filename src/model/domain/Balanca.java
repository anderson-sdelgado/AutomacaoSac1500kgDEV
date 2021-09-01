/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.domain;

/**
 *
 * @author anderson
 */
public class Balanca {
 
    private int idBalanca;
    private int numBalanca;
    private String descrBalanca;
    private String ipBalanca;

    public Balanca(int numBalanca) {
        this.numBalanca = numBalanca;
    }
    
    public int getIdBalanca() {
        return idBalanca;
    }

    public void setIdBalanca(int idBalanca) {
        this.idBalanca = idBalanca;
    }

    public int getNumBalanca() {
        return numBalanca;
    }

    public void setNumBalanca(int numBalanca) {
        this.numBalanca = numBalanca;
    }

    public String getDescrBalanca() {
        return descrBalanca;
    }

    public void setDescrBalanca(String descrBalanca) {
        this.descrBalanca = descrBalanca;
    }

    public String getIpBalanca() {
        return ipBalanca;
    }

    public void setIpBalanca(String ipBalanca) {
        this.ipBalanca = ipBalanca;
    }


    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.domain;

/**
 *
 * @author anderson
 */
public class Ensaque {

    private long id;
    private String numBalanca;
    private String numIDSaco;
    private String pesoLiq;
    private String data;
    private String hora;
    private String lote;
    private String cor;

    public Ensaque() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        
        this.cor = cor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNumBalanca() {
        return numBalanca;
    }

    public void setNumBalanca(String numBalanca) {
        this.numBalanca = numBalanca;
    }

    public String getNumIDSaco() {
        return numIDSaco;
    }

    public void setNumIDSaco(String numIDSaco) {
        this.numIDSaco = numIDSaco;
    }

    public String getPesoLiq() {
        return pesoLiq;
    }

    public void setPesoLiq(String pesoLiq) {
        this.pesoLiq = pesoLiq.replace(",", ".");
    }

}

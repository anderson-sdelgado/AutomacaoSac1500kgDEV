/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author anderson
 */
public class BigBag extends Ensaque {

    private boolean verif;
    private boolean captDados;
    private static BigBag instance = null;
    
    public static BigBag getInstance() {
        if (instance == null)
            instance = new BigBag();
        return instance;
    }

    public BigBag getBigBag(String frase, int balanca) {
        if (balanca == 3) {
            char caracInicial = 'P';
            char caracFinal = 'T';
            verif = false;
            for (int i = 0; i < frase.length(); i++) {
                if (frase.charAt(i) == caracFinal) {
                    captDados = false;
                    String data = "dd/MM/yyyy";
                    String hora = "HH:mm";
                    SimpleDateFormat dataFormat = new SimpleDateFormat(data);
                    SimpleDateFormat horaFormat = new SimpleDateFormat(hora);
                    Date dataHora = new Date();
                    setNumBalanca(String.valueOf(balanca));
                    setPesoLiq(getPesoLiq().substring(3, 10).trim());
                    setHora(horaFormat.format(dataHora));
                    setData(dataFormat.format(dataHora));
                    verif = true;
                }
                if (frase.charAt(i) == caracInicial) {
                    captDados = true;
                    setPesoLiq("");
                }
                if (captDados) {
                    setPesoLiq(getPesoLiq() + frase.charAt(i));
                }
            }
        } else if (balanca == 5) {
            setNumBalanca(String.valueOf(balanca));
            setPesoLiq(frase.substring(7, 15).trim());
            setHora(frase.substring(29, 37).trim());
            setData(frase.substring(18, 28).trim());
            verif = true;
        }
        return this;
    }

    public boolean isVerif() {
        return verif;
    }

    public void setVerif(boolean verif) {
        this.verif = verif;
    }

}

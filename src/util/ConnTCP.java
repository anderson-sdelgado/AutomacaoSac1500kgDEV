/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


import control.BigBagCTR;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import model.domain.Balanca;

/**
 *
 * @author anderson
 */
public class ConnTCP extends Thread {

    private Socket socketCliente;
    private Balanca balanca;
    private BigBagCTR bigBagCTR;
    
    public ConnTCP(Balanca balanca, BigBagCTR bigBagCTR) {
        this.balanca = balanca;
        this.bigBagCTR = bigBagCTR;
    }
    
    public void run(){
        try {
            String frase;
            System.out.println("Conexao1");
            socketCliente = new Socket(this.balanca.getIpBalanca(), 5000);
            System.out.println("Conexao4");
            InputStreamReader inputStreamReader = new InputStreamReader(socketCliente.getInputStream());
            while(socketCliente.isConnected()){
                BufferedReader doServidor = new BufferedReader(inputStreamReader);
                System.out.println("Conexao5");
                frase = doServidor.readLine();
                System.out.println("Conexao6");
                System.out.println("Do Servidor: " + frase);
                if(!frase.equals("")){
                    System.gc();
                    bigBagCTR.salvarEnsaqueBigBag(frase, balanca);
                }
            }
        } catch (Exception ex) {
            System.out.println("Erro Balanca 1 = " + ex);
            try {
                if(socketCliente != null)
                    socketCliente.close();
            } catch (Exception ex1) {
                System.out.println("Erro = " + ex1);
            }
        } finally {
        }
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.tcp;

import business.bo.ManipPesBalanca2BO;
import business.bo.PingarIpBalanca2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentation.gui.TelaPrincipal;

/**
 *
 * @author anderson
 */
public class ConnTCPBalanca2 extends Thread  {

    private String pesagem;
    private Thread thread;
    //private FacadeBO facadeBO;
    private BufferedReader doServidor;
    //private TimerBO timerBO;
    //private PesagemEsteira pesEsteira;
    private String[] dados = new String [7];
    private long lote;
    private FileWriter fw;
    private BufferedWriter bw;
    private TelaPrincipal principal;
    private Socket socketCliente;
    private PingarIpBalanca2 pingarIp;

    public ConnTCPBalanca2() {
    }

    public ConnTCPBalanca2(TelaPrincipal principal, PingarIpBalanca2 pingarIp) {
        this.principal = principal;
        this.pingarIp = pingarIp;
    }

    public void run(){
        try {

            // TODO code application logic here
            String fraseModificada;
            System.out.println("Conexao1");
            socketCliente = new Socket("192.168.3.18", 5000);
            System.out.println("Conexao4");
            InputStreamReader inputStreamReader = new InputStreamReader(socketCliente.getInputStream());

            while(socketCliente.isConnected()){

                BufferedReader doServidor = new BufferedReader(inputStreamReader);
                System.out.println("Conexao5");
                fraseModificada = doServidor.readLine();
                System.out.println("Conexao6");
                System.out.println("Do Servidor: " + fraseModificada);
                
                if(!fraseModificada.equals("")){
                    pingarIp.setContador(0);
                    System.gc();
                    ManipPesBalanca2BO manipulacaoPesagemBO = new ManipPesBalanca2BO(this.principal);
                    manipulacaoPesagemBO.manipulandoStringPes(fraseModificada);
                }
                
            }

        } catch (Exception ex) {
            System.out.println("Erro Balanca 2 = " + ex);
            try {
                if(socketCliente != null)
                    socketCliente.close();
                if(doServidor != null)
                    doServidor.close();
                //principal.setConectadoSocket(false);
                //pingarIp.setContador(0);
                //this.stop();
            } catch (Exception ex1) {
                Logger.getLogger(ConnTCPBalanca2.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {

        }
    }

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.tcp;

import business.bo.ManipPes2Ensac1200BO;
import business.bo.ManipPesBalanca1BO;
import business.bo.PingarIp2Ensac1200;
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
public class ConnTCP2Ensac1200 extends Thread  {

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
    private PingarIp2Ensac1200 pingarIp;

    public ConnTCP2Ensac1200() {
    }

    public ConnTCP2Ensac1200(TelaPrincipal principal, PingarIp2Ensac1200 pingarIp) {
        this.principal = principal;
        this.pingarIp = pingarIp;
    }
    
        public ConnTCP2Ensac1200(TelaPrincipal principal) {
        this.principal = principal;
    }

    public void run(){
        try {

            // TODO code application logic here
            String fraseModificada;
            System.out.println("Conexao1");
            socketCliente = new Socket("192.168.1.84", 5000);
            System.out.println("Conexao4");
            InputStreamReader inputStreamReader = new InputStreamReader(socketCliente.getInputStream());

            while(socketCliente.isConnected()){

                BufferedReader doServidor = new BufferedReader(inputStreamReader);
                System.out.println("Conexao5");
                fraseModificada = doServidor.readLine();
                System.out.println("Conexao6");
                System.out.println("Do Servidor: " + fraseModificada);
                
                if(!fraseModificada.equals("")){
                    //pingarIp.setContador(0);
                    System.gc();
                    ManipPes2Ensac1200BO manipPes2Ensac1200BO = new ManipPes2Ensac1200BO(this.principal);
                    manipPes2Ensac1200BO.manipulandoStringPes(fraseModificada);
                }
                
            }

        } catch (Exception ex) {
            System.out.println("Erro Balanca 1 = " + ex);
            try {
                if(socketCliente != null)
                    socketCliente.close();
                if(doServidor != null)
                    doServidor.close();
                //principal.setConectadoSocket(false);
                //pingarIp.setContador(0);
                //this.stop();
            } catch (Exception ex1) {
                Logger.getLogger(ConnTCP2Ensac1200.class.getName()).log(Level.SEVERE, null, ex1);
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

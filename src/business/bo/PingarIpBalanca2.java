
package business.bo;

import connection.tcp.ConnTCPBalanca2;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentation.gui.TelaPrincipal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anderson
 */
public class PingarIpBalanca2 extends Thread {

    private TelaPrincipal principal;
    private ConnTCPBalanca2 connectionTCP;
    private int contador;

    public PingarIpBalanca2(TelaPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public void run(){

        try {
            
            while(true){
                String resposta = "";
                int fim = 0;
                boolean kbo = false;
                String comando = new String("ping -n 1 -w 600 192.168.3.18");
                    try {
                        Scanner s = new Scanner(Runtime.getRuntime().exec("cmd /c " + comando).getInputStream());
                        while(s.hasNextLine()) {
                            
                            resposta = s.nextLine()+"\n";
//                            fim = resposta.length()-13;
                            fim = resposta.length()-12;

                            for (int j=0;j<=fim;j++){
//                                if (resposta.substring(j, 13+j).equals("Recebidos = 1")){
                                if (resposta.substring(j, 12+j).equals("Received = 1")){
                                    kbo = true;
                                    principal.setRespPing2(true);
                                    break;
                                }
                            }

                            if (kbo == true)
                                break;
                            else
                                principal.setRespPing2(false);

                        }

                        s.close();

                    } catch (Exception e) {
                        
                    }

                if(contador > 30){
                    contador = 0;
                    if(connectionTCP.getSocketCliente() != null){
                        connectionTCP.getSocketCliente().close();
                    }
                    connectionTCP.stop();
                    principal.setConecSocket2(false);
                }

                if(principal.isRespPing2() && (contador != -1)){
                    contador = contador + 1;
                }

                if((contador == -1) && principal.isRespPing2()){
                    contador = 0;
                }

                if(principal.isRespPing2() && !principal.isConecSocket2()){
                    connectionTCP = new ConnTCPBalanca2(principal, this);
                    connectionTCP.start();
                    principal.setConecSocket1(true);
                }
                else if(!principal.isRespPing2() && principal.isConecSocket2()){
                    contador = -1;
                    if(connectionTCP.getSocketCliente() != null){
                        connectionTCP.getSocketCliente().close();
                    }
                    connectionTCP.stop();
                    principal.setConecSocket1(false);
                }

                System.out.print("\n Ping = " + resposta + " Contador = " + contador);
                sleep(2000);
                
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex);
        }
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
}

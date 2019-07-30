/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.bo;

import connection.tcp.ConnTCP2Ensac1200;
import static java.lang.Thread.sleep;
import java.util.Scanner;
import presentation.gui.TelaPrincipal;

/**
 *
 * @author anderson
 */
public class PingarIp2Ensac1200 extends Thread {
    
    private TelaPrincipal principal;
    private ConnTCP2Ensac1200 connectionTCP;
    private int contador;

    public PingarIp2Ensac1200(TelaPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public void run(){

        try {
            
            while(true){
                String resposta = "";
                int fim = 0;
                boolean kbo = false;
                String comando = new String("ping -n 1 -w 600 192.168.1.84");
                    try {
                        Scanner s = new Scanner(Runtime.getRuntime().exec("cmd /c " + comando).getInputStream());
                        while(s.hasNextLine()) {
                            
                            resposta = s.nextLine()+"\n";
                            fim = resposta.length()-13;
//                            fim = resposta.length()-12;

                            for (int j=0;j<=fim;j++){
                                if (resposta.substring(j, 13+j).equals("Recebidos = 1")){
                                //if (resposta.substring(j, 12+j).equals("Received = 1")){
                                    kbo = true;
                                    principal.setRespPing1(true);
                                    break;
                                }
                            }

                            if (kbo == true)
                                break;
                            else
                                principal.setRespPing1(false);

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
                    principal.setConecSocket1(false);
                }

                if(principal.isRespPing1() && (contador != -1)){
                    contador = contador + 1;
                }

                if((contador == -1) && principal.isRespPing1()){
                    contador = 0;
                }

                if(principal.isRespPing1() && !principal.isConecSocket1()){
                    connectionTCP = new ConnTCP2Ensac1200(principal, this);
                    connectionTCP.start();
                    principal.setConecSocket1(true);
                }
                else if(!principal.isRespPing1() && principal.isConecSocket1()){
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

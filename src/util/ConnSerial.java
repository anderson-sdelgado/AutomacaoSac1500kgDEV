/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fazecast.jSerialComm.SerialPort;
import control.BigBagCTR;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.domain.Balanca;

/**
 *
 * @author anderson
 */
public class ConnSerial implements Runnable {

    public int nodeBytes;
    private SerialPort serialPort;
    private InputStream entrada;
    private Thread threadLeitura;
    private Balanca balanca;
    private BigBagCTR bigBagCTR;

    public ConnSerial(Balanca balanca, BigBagCTR bigBagCTR) {
        this.balanca = balanca;
        this.bigBagCTR = bigBagCTR;
    }

    public void inicialConexao() {
        try {

            serialPort = SerialPort.getCommPort("COM1");
            serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            serialPort.openPort();

            if (serialPort.isOpen()) {
                entrada = serialPort.getInputStream();
                threadLeitura = new Thread(this);
                threadLeitura.start();
                System.out.println(serialPort.getDescriptivePortName() + " SUCESSO. Porta aguardando...");
            } else {
                System.out.println(serialPort.getDescriptivePortName() + " FALHA DE ABERTURA ");
            }

        } catch (Exception e) {
            System.out.println("Erro durante o procedimento. STATUS" + e);
            System.exit(1);
        }
    }

    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
        try {
            while (true) {
                String dadosRec = in.readLine();
                System.out.println("Retorno = " + dadosRec);
                bigBagCTR.salvarEnsaqueBigBag(dadosRec, balanca);
            }
//            in.close();
        } catch (Exception e) {
            System.out.println("Erro. Status = " + e);
        }
    }

    public void FecharCom() {
        try {
            serialPort.closePort();
            System.out.println("CONEXAO FECHADA>>FIM..");
        } catch (Exception e) {
            System.out.println("ERRO AO FECHAR. STATUS: " + e);
            System.exit(0);
        }
    }

}

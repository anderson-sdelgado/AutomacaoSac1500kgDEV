/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import control.BigBagCTR;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.InputStream;
import model.domain.Balanca;

/**
 *
 * @author anderson
 */
public class ConnSerial implements Runnable, SerialPortEventListener {

    public int nodeBytes;
    private CommPortIdentifier cp;
    private SerialPort porta;
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
            cp = CommPortIdentifier.getPortIdentifier("COM1");
            System.out.println("Obteve a  porta = COM1");
            if (cp == null) {
                System.out.println("A COM1 nao existe!");
                System.exit(1);
            }
            System.out.println("Porta abrir");
            porta = (SerialPort) cp.open("SComm", 1000);
            System.out.println("Porta aberta com sucesso!");

            porta.setSerialPortParams(9600, porta.DATABITS_8, porta.STOPBITS_1, porta.PARITY_NONE);

            try {
                entrada = porta.getInputStream();
                System.out.println("FLUXO OK!");
            } catch (Exception e) {
                System.out.println("Erro.STATUS: " + e);
                System.exit(1);
            }
            try {
                porta.addEventListener(this);
                System.out.println("SUCESSO. Porta aguardando...");
            } catch (Exception e) {
                System.out.println("Erro ao criar listener: ");
                System.out.println("STATUS: " + e);
                System.exit(1);
            }
            porta.notifyOnDataAvailable(true);
            try {
                threadLeitura = new Thread(this);
                threadLeitura.start();
            } catch (Exception e) {
                System.out.println("Erro ao iniciar leitura: " + e);
            }

        } catch (Exception e) {
            System.out.println("Erro durante o procedimento. STATUS" + e);
            System.exit(1);
        }
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Erro. Status = " + e);
        }
    }

    public void serialEvent(SerialPortEvent ev) {
        switch (ev.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] bufferLeitura = new byte[20];
                try {
                    while (entrada.available() > 0) {
                        nodeBytes = entrada.read(bufferLeitura);
                    }
                    String dadosRec = new String(bufferLeitura);
                    if (bufferLeitura.length == 0) {
                        System.out.println("Nada lido!");
                    } else if (bufferLeitura.length == 1) {
                        System.out.println("Apenas um byte foi lido!");
                    } else {
                        System.out.println("Dados recebido: " + dadosRec);
                        bigBagCTR.salvarEnsaqueBigBag(dadosRec, balanca);
                    }
                } catch (Exception e) {
                    System.out.println("Erro durante a leitura: " + e);
                }
                break;
        }
    }

    public void FecharCom() {
        try {
            porta.close();
            System.out.println("CONEXAO FECHADA>>FIM..");
        } catch (Exception e) {
            System.out.println("ERRO AO FECHAR. STATUS: " + e);
            System.exit(0);
        }
    }

}

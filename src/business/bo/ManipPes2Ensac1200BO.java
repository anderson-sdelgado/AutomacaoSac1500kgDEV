/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business.bo;

import business.to.PesagemEsteira;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.ref.WeakReference;
import persistence.pst.bd.EsteiraPST;
import presentation.gui.TelaPrincipal;

/**
 *
 * @author anderson
 */
public class ManipPes2Ensac1200BO extends Thread {
    
    private String pesagem;
    private Thread thread;
    private FileWriter fw;
    private BufferedWriter bw;
    private TelaPrincipal principal;

    public ManipPes2Ensac1200BO(TelaPrincipal principal) {
        this.principal = principal;
    }

    public void manipulandoStringPes(String pesagem){
        this.pesagem = pesagem;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        System.gc();
        PesagemEsteira pesEsteira = new PesagemEsteira();
        pesEsteira.setNumBalanca("5");
        pesEsteira.setPesoLiq(pesagem.substring(7, 15).trim());
        pesEsteira.setHora(pesagem.substring(29, 37).trim());
        pesEsteira.setData(pesagem.substring(18, 28).trim());

        System.out.println("NumBalanca = " + pesEsteira.getNumBalanca());
        System.out.println("PesoLiq = " + pesEsteira.getPesoLiq());
        System.out.println("Hora = " + pesEsteira.getHora());
        System.out.println("Data = " + pesEsteira.getData());

        this.principal.mostrarTela(pesEsteira);
        
        System.gc();
        EsteiraPST esteiraPST = new EsteiraPST();
        esteiraPST.addPesagem(pesEsteira);


    }
    
}

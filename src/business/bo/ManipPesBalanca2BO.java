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
public class ManipPesBalanca2BO extends Thread {
    
    private String pesagem;
    private Thread thread;
    private FileWriter fw;
    private BufferedWriter bw;
    private TelaPrincipal principal;

    public ManipPesBalanca2BO(TelaPrincipal principal) {
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
        pesEsteira.setNumBalanca(pesagem.substring(2, 3));
        pesEsteira.setNumIDSaco(pesagem.substring(4, 8));
        pesEsteira.setPesoLiq(pesagem.substring(10, 15));
        pesEsteira.setHora(pesagem.substring(18, 26));
        pesEsteira.setData(pesagem.substring(27, 37));
        pesEsteira.setLote(pesagem.substring(40, 50));
        pesEsteira.setCor(pesagem.substring(53, 56));

        System.out.println("NumBalanca = " + pesEsteira.getNumBalanca());
        System.out.println("NumIdSaco = " + pesEsteira.getNumIDSaco());
        System.out.println("PesoLiq = " + pesEsteira.getPesoLiq());
        System.out.println("Hora = " + pesEsteira.getHora());
        System.out.println("Data = " + pesEsteira.getData());
        System.out.println("Lote = " + pesEsteira.getLote());
        System.out.println("Cor = " + pesEsteira.getCor());

        this.principal.mostrarTela(pesEsteira);
        
        System.gc();
        EsteiraPST esteiraPST = new EsteiraPST();
        esteiraPST.addPesagem(pesEsteira);


    }
    
}

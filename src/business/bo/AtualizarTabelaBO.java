/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business.bo;

import java.util.logging.Level;
import java.util.logging.Logger;
import presentation.gui.TelaPrincipal;

/**
 *
 * @author anderson
 */
public class AtualizarTabelaBO extends Thread {

    private TelaPrincipal telaPrincipal;
    private String[] dados;

    public AtualizarTabelaBO() {
    }

    public AtualizarTabelaBO(TelaPrincipal telaPrincipal, String[] dados) {
        this.telaPrincipal = telaPrincipal;
        this.dados = dados;
    }

    public void run(){
        //telaPrincipal.getDefaultTableModel().insertRow(0, dados);
        //telaPrincipal.pack();
    }

}

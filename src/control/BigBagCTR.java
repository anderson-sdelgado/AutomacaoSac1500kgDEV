/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.dao.BigBagDAO;
import model.domain.Balanca;
import model.domain.BigBag;
import util.ConnSerial;
import util.ConnTCP;
import view.TelaPrincipal;

/**
 *
 * @author anderson
 */
public class BigBagCTR {

    private TelaPrincipal telaPrincipal;
    
    public BigBagCTR(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }
    
    public void startCapturaEnsaqueBigBag1(){
        
        Balanca balanca = new Balanca(3);
        
        ConnSerial connSerial = new ConnSerial(balanca, this);
        connSerial.inicialConexao();
        
    }
    
    public void startCapturaEnsaqueBigBag2(){
        
        Balanca balanca = new Balanca(5);
        BigBagDAO bigBagDAO = new BigBagDAO();
        balanca = bigBagDAO.getBalanca(balanca);
  
        ConnTCP connTCP = new ConnTCP(balanca, this);
        connTCP.start();
        
    }
    
    public void salvarEnsaqueBigBag(String frase, Balanca balanca){
        
        BigBag.getInstance().getBigBag(frase, balanca.getNumBalanca());
        if(BigBag.getInstance().isVerif()){
            this.telaPrincipal.mostrarTela(BigBag.getInstance());
            BigBagDAO bigBagDAO = new BigBagDAO();
            bigBagDAO.addBigBag(BigBag.getInstance());
        }
        
    }
    
    
    
}

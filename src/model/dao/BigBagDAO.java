/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.domain.Balanca;
import model.domain.BigBag;
import util.ConnBD;

/**
 *
 * @author anderson
 */
public class BigBagDAO {

    public BigBagDAO() {
    }

    public int addBigBag(BigBag bigBag) {
        
        String sql = "INSERT INTO "
                + " INTERFACE.AUTOMACAO_ESTEIRA "
                + " ( ID"
                + " , NUMBALANCA "
                + " , NUMIDSACO "
                + " , PESOLIQ "
                + " , DATAHORA "
                + " , LOTE "
                + " , COR "
                + " , STATUS) "
                + " VALUES "
                + " ( " + getID()
                + " , " + bigBag.getNumBalanca() + " "
                + " , null "
                + " , " + bigBag.getPesoLiq() + " "
                + " , TO_DATE('" + bigBag.getData() + " " + bigBag.getHora() + "','DD/MM/YYYY HH24:MI:SS') "
                + " ,  null , null, 0)";
        
        return ConnBD.getInstance().manipBDDefault(sql);
        
    }

    public Balanca getBalanca(Balanca balanca) {

        try {

            String sql = "SELECT"
                    + " ID "
                    + " , DESCR "
                    + " , IP "
                    + " FROM "
                    + " INTERFACE.AUTOMACAO_ENSAQUE_IP "
                    + " WHERE "
                    + " NUMBALANCA = " + balanca.getNumBalanca();

            Statement stmt = ConnBD.getInstance().getConnection().createStatement();
            ResultSet rSet = stmt.executeQuery(sql);
            
            while (rSet.next()) {
                balanca.setIdBalanca(Integer.parseInt(rSet.getString(1)));
                balanca.setDescrBalanca(rSet.getString(2));
                balanca.setIpBalanca(rSet.getString(3));
            }

        } catch (Exception e) {
            System.out.println("Falha = " + e);
        }

        return balanca;
    }
    
    private long getID() {

        long id = 0;
        
        try {

            String sql = "SELECT "
                            + " MAX(ID) "
                        + " FROM "
                            + " INTERFACE.AUTOMACAO_ESTEIRA ";

            Statement stmt = ConnBD.getInstance().getConnection().createStatement();
            ResultSet rSet = stmt.executeQuery(sql);
            
            while (rSet.next()) {
                id = Long.parseLong(rSet.getString(1));
            }

        } catch (Exception e) {
            System.out.println("Falha = " + e);
        }

        return id;
    }

}

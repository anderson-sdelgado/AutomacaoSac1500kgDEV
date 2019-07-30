/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence.pst.bd;

import business.to.PesagemEsteira;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anderson
 */
public class EsteiraPST {

    private ArrayList pesagens;

    public EsteiraPST() {
    }

    public void addPesagem(PesagemEsteira pesEsteira){

        String sql = "";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rSet = null;

        try
        {

            int codigo = returnCont();
            
            System.gc();
            
            conn = ConnectionPST.getInstance().getConnection();
            stmt = conn.createStatement();
            sql = "INSERT INTO "
                        + " INTERFACE.AUTOMACAO_ESTEIRA "
                    + " VALUES "
                        + " ( " + codigo + " "
                        + " , " + pesEsteira.getNumBalanca() +" "
                        + " , null "
                        + " , " + pesEsteira.getPesoLiq() + " "
                        + " , TO_DATE('"+ pesEsteira.getData() +" " + pesEsteira.getHora() + "','DD/MM/YYYY HH24:MI:SS') "
                        + " ,  null , null, 0)";
            System.out.println("SQL = " + sql);
            stmt.executeUpdate(sql);

            try {
                if (rSet != null)
                    rSet.close();
                if (stmt != null)
                    stmt.close();
//                if (conn != null)
//                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }catch(Exception e){
            System.out.println("Erro2 = " + e);

        } 

    }

    public int returnCont(){

        String sql = "";
        int codigo = 0;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rSet = null;

        try
        {

            System.gc();

            conn = ConnectionPST.getInstance().getConnection();
            stmt = conn.createStatement();
            rSet = stmt.executeQuery("SELECT MAX(ID) FROM INTERFACE.AUTOMACAO_ESTEIRA");

            while(rSet.next())
            {
                codigo = Integer.parseInt(rSet.getString(1));
                codigo = codigo + 1;
            }

            try {
                if (rSet != null)
                    rSet.close();
                if (stmt != null)
                    stmt.close();
//                if (conn != null)
//                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }catch(Exception e){
            System.out.println("Erro2 = " + e);
        }

        return codigo;

    }
    
}

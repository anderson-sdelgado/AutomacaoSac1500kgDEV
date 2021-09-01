/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.*;

/**
 *
 * @author Anderson
 */
public class ConnBD {

    private int BD = 2;
    public final static String driver = "oracle.jdbc.driver.OracleDriver";
    public final static String url_prod = "jdbc:oracle:thin:@(DESCRIPTION = (ENABLE = BROKEN)(FAILOVER = ON)(LOAD_BALANCE = YES)"
            + "    (ADDRESS = (PROTOCOL = TCP)(HOST = stafe-scan)(PORT = 1521))"
            + "    (CONNECT_DATA ="
            + "      (SERVER = DEDICATED)"
            + "      (SERVICE_NAME = STAFE)"
            + "      (FAILOVER_MODE ="
            + "        (TYPE = SELECT)"
            + "        (METHOD = BASIC)"
            + "        (RETRIES = 180)"
            + "        (DELAY = 5)"
            + "       )"
            + "    )"
            + "  )";

    public final static String url_qa = "jdbc:oracle:thin:@(DESCRIPTION = (ENABLE = BROKEN)(FAILOVER = ON)(LOAD_BALANCE = YES)"
            + "    (ADDRESS = (PROTOCOL = TCP)(HOST = stafe-scan)(PORT = 1521))"
            + "    (CONNECT_DATA ="
            + "      (SERVER = DEDICATED)"
            + "      (SERVICE_NAME = STAFEQA)"
            + "      (FAILOVER_MODE ="
            + "        (TYPE = SELECT)"
            + "        (METHOD = BASIC)"
            + "        (RETRIES = 180)"
            + "        (DELAY = 5)"
            + "       )"
            + "    )"
            + "  )";

    public final static String url_dev = "jdbc:oracle:thin:@(DESCRIPTION = (ENABLE = BROKEN)(FAILOVER = ON)(LOAD_BALANCE = YES)"
            + " (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.2.15)(PORT = 1521)) "
            + " (CONNECT_DATA = "
            + "      (SERVER = DEDICATED) "
            + "      (SERVICE_NAME = STAFEDEV) "
            + "      (FAILOVER_MODE = "
            + "        (TYPE = SELECT) "
            + "        (METHOD = BASIC) "
            + "        (RETRIES = 180) "
            + "        (DELAY = 5) "
            + "       )"
            + "    )"
            + "  )";

    public final static String usuario = "INTERFACE";
    public final static String senha = "FGBNY946";

    private Connection connection = null;

    private static ConnBD instance = null;

    private ConnBD() {
        inicializar();
    }

    public static ConnBD getInstance() {
        if (instance == null) {
            instance = new ConnBD();
        }
        return instance;
    }

    private void inicializar() {
        try {
            Class.forName(driver);
            String url = "";
            switch (BD) {
                case 1:
                    url = url_prod;
                    break;
                case 2:
                    url = url_qa;
                    break;
                case 3:
                    url = url_dev;
                    break;
                default:
                    break;
            }

            connection = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException e) {
            System.out.println("O driver nao pode ser carregado ["
                    + e.getMessage() + "]");
        } catch (SQLException e) {
            System.out.println("A conexao nao pode ser estabelecida ["
                    + e.getMessage() + "]");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexao ["
                    + e.getMessage() + "]");
        }
    }

    public int manipBDDefault(String sql) {
        int r = 0;
        try {
            Statement stmt = getConnection().createStatement();
            r = stmt.executeUpdate(sql);
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return r;
    }


}

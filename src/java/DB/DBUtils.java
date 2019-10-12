/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author NguyenNTSE63030
 */
public class DBUtils implements Serializable {

    public static Connection makeConnection() throws NamingException, SQLException, ClassNotFoundException {
//        Context context = new InitialContext(); // context hien hanh o phia client
//        Context tomcatCtx = (Context) context.lookup("java:comp/env");// context o phia server
//        DataSource ds = (DataSource) tomcatCtx.lookup("PRX");
//
//        Connection con = ds.getConnection();
//        return con;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Spa";
        Connection con = DriverManager.getConnection(url, "sa", "");
        return con;
    }

}

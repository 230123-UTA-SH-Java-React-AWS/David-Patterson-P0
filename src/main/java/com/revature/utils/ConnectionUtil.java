package com.revature.utils;
import java.sql.*;
//responsible for connecting to a data
public class ConnectionUtil {

    private static Connection con;

    private ConnectionUtil(){
        con = null;
    }

    public static Connection getConnection(){
        try {
            if (con != null && !con.isClosed()){
                return con;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String url, user, pass;

        //System.out.println(System.getenv("url"));
        //System.out.println(System.getenv("user"));
        //System.out.println(System.getenv("serverpassword"));

        url = System.getenv("url");
        user = System.getenv("user");
        pass = System.getenv("serverpassword");

        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("you probably put in the wrong password or url");
        }

        return con;


    }
}

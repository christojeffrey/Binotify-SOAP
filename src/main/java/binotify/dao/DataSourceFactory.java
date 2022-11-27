package binotify.dao;

import java.sql.*;

public class DataSourceFactory {
    
    
    
    
    private static String HOST = "localhost";
    private static String PORT = "3306";
    private static String DATABASE = "binotify_subscriptions";
    private static String USER = "root";
    private static String PASSWORD = "root";

    private Connection conn = null;
    
    private DataSourceFactory() {
        String url = String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, DATABASE);
        // print
        System.out.println(url);
        try {
            this.conn = DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    
    private static DataSourceFactory instance = new DataSourceFactory();


    public static DataSourceFactory getInstance() {
        return instance;
    }

    // this is the public method to get the connection
    public static Connection getConn() {
        return instance.conn;
    }
}

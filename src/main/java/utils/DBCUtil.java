package utils;

import utils.PropertyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCUtil {
    private static final String DBDRIVER = PropertyUtil.getProperty("jdbc.driver");
    private static final String DBURL = PropertyUtil.getProperty("jdbc.url");
    private static final String DBUSER = PropertyUtil.getProperty("jdbc.user");
    private static final String DBPASS =PropertyUtil.getProperty("jdbc.password");

    private Connection conn;

    public DBCUtil(){
        try {
            Class.forName(DBDRIVER);
            this.conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn(){
        return this.conn;
    }
    public void close(){
        if (this.conn != null){
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

package database.MysqlSettings;
import com.mysql.cj.jdbc.DatabaseMetaData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MysqlSettings {
    public static final String dirver = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://106.15.35.61:3306/db_lab";
    public static final String user = "root";
    public static final String password = "20011024yangshuo";

    public static Connection getMysqlConnection(){
        Connection connection = null;
        try{
            Class.forName(dirver);
            connection = DriverManager.getConnection(url,user,password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeMysqlConnection(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

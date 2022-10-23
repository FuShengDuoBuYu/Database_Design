package database.MysqlSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MysqlSettings {
    //获取项目路径
    private static File file = new File(System.getProperty("user.dir")+"/../configInfo.txt");
    private static Scanner scanner;

    static {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final String dirver = "com.mysql.cj.jdbc.Driver";
    public static final String url = scanner.nextLine();
    public static final String user = scanner.nextLine();
    public static final String password = scanner.nextLine();

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

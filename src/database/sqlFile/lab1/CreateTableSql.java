package database.sqlFile.lab1;

import database.MysqlSettings.MysqlSettings;
import database.SQLExecute;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Scanner;

public class CreateTableSql {

    public static void createTable(Connection connection){
        String path = System.getProperty("user.dir");
        File file = new File(path+"\\src\\database\\sqlFile\\lab1\\createTableSql.sql");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String sql = scanner.nextLine();
                SQLExecute.createTable(connection, sql);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            MysqlSettings.closeMysqlConnection(connection);
        }
    }
}

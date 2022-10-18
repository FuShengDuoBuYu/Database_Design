package database.sqlUtils;

import com.mysql.cj.jdbc.DatabaseMetaData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static database.MysqlSettings.MysqlSettings.closeMysqlConnection;

public class TableInfo {
    public static Map<String,String> getTableStructure(Connection connection, String tableName){
        Map<String,String> tableStructure = new LinkedHashMap<>();
        try{
            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "%", tableName, "%");
            while (resultSet.next()){
                tableStructure.put(resultSet.getString("COLUMN_NAME"),resultSet.getString("TYPE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeMysqlConnection(connection);
        }
        return tableStructure;
    }
}

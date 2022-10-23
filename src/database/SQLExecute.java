package database;

import database.sqlUtils.DataHandle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class SQLExecute {
    public static void createTable(Connection connection,String createTableSql){

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSql);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertData(Connection connection, ArrayList<String> insertData, String tableName, Map<String,String> tableStructure){
        //设置插入语句
        String insertSql = "insert into "+tableName+" values(" ;
        for (int i = 0; i < tableStructure.size(); i++) {
            insertSql+="?,";
        }
        insertSql = insertSql.substring(0,insertSql.length()-1);
        insertSql+=");";
        //执行插入语句
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            for (int i = 1; i < insertData.size(); i++) {
                ArrayList<Object> dataToSql = DataHandle.stringToSql(insertData.get(i),tableStructure);
                for (int j = 0; j < dataToSql.size(); j++) {
                    preparedStatement.setObject(j+1,dataToSql.get(j));
                }
                //后面的补充null
                for (int j = dataToSql.size(); j < tableStructure.size(); j++) {
                    preparedStatement.setObject(j+1,null);
                }
                try {
                    preparedStatement.execute();
                }
                catch (Exception e){
                    System.out.println("第"+i+"行数据插入失败");
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

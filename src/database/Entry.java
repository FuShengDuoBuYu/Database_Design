package database;

import database.MysqlSettings.MysqlSettings;
import database.sqlFile.lab1.CreateTableSql;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static database.sqlUtils.TableInfo.getTableStructure;

public class Entry {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        //表名
        String[] tableName = {"room","student"};
        //创建表
        CreateTableSql.createTable(MysqlSettings.getMysqlConnection());
        //获取表的结构
        Map<String,String> roomColumnInfo = getTableStructure(MysqlSettings.getMysqlConnection(),tableName[0]);
        Map<String,String> studentColumnInfo = getTableStructure(MysqlSettings.getMysqlConnection(),tableName[1]);
        //获取表的内容
        ArrayList<String> studentLines = FileIOUtil.getStringLinesFromFile("\\src\\database\\sqlFile\\lab1\\student.csv");
        ArrayList<String> roomLines = FileIOUtil.getStringLinesFromFile("\\src\\database\\sqlFile\\lab1\\room.csv");
        //导入room表
        SQLExecute.insertData(MysqlSettings.getMysqlConnection(),roomLines,tableName[0],roomColumnInfo);
        //导入student表
        SQLExecute.insertData(MysqlSettings.getMysqlConnection(),studentLines,tableName[1],studentColumnInfo);
    }
}

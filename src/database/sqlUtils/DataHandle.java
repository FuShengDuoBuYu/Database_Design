package database.sqlUtils;

import java.util.ArrayList;
import java.util.Map;

public class DataHandle {
    //将string转为sql语句
    public static ArrayList<Object> stringToSql(String data, Map<String,String> tableStructure){
        //去掉双引号
        data = data.replace("\"","");
        //分割
        String[] dataSplit = data.split(",");
        //获取表的结构的value
        ArrayList<String> tableStructureValue = new ArrayList<>();
        for (String key : tableStructure.keySet()) {
            tableStructureValue.add(tableStructure.get(key));
        }
        //将数据根据key的类型转换为对应的类型
        ArrayList<Object> dataToSql = new ArrayList<>();
        for (int i = 0; i < dataSplit.length; i++) {
            String type = tableStructureValue.get(i);
            switch (type){
                case "INT":
                    dataToSql.add(Integer.parseInt(dataSplit[i]));
                    break;
                case "VARCHAR":
                    dataToSql.add(dataSplit[i].trim());
                    break;
                case "DOUBLE":
                    dataToSql.add(Double.parseDouble(dataSplit[i]));
                    break;
                default:
                    break;
            }
        }

        return dataToSql;
    }
}

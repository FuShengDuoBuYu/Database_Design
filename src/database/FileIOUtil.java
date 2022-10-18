package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIOUtil {
    public static ArrayList<String> getStringLinesFromFile(String filepath){
        //根据filepath读取文件，返回一个ArrayList<String>，每个元素是文件中的一行
        ArrayList<String> res = new ArrayList<>();
        //获取当前项目目录
        String currentPath = System.getProperty("user.dir");
        //拼接文件路径
        String filePath = currentPath + filepath;
        //用Sanner读取文件
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file,getFileCharset(filePath));
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                res.add(s);
            }

        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
            e.printStackTrace();
        }
        finally {
            if(scanner!=null){
                scanner.close();
            }
        }
        return res;
    }

    //判断文件的字符集格式
    public static String getFileCharset(String filePath) {
        File file = new File(filePath);
        InputStream in= null;
        try {
            in = new FileInputStream(file);
            byte[] b = new byte[3];
            in.read(b);
            in.close();
            if (b[0] == 34 && b[1] == 114 && b[2] == 101)
                return "UTF-8";
            else
                return "GBK";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

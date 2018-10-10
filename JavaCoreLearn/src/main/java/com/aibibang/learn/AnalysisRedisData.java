package com.aibibang.learn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 分析redis aof文件
 *
 * @author Truman
 * @create 2017-05-12 13:54
 * @description :
 **/
public class AnalysisRedisData {
    public static void main(String[] args) {
        readAofFile("E:\\appendonly.aof");
    }

    public static void readAofFile(String filePath) {
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int j = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //System.out.println(lineTxt);
                    if (lineTxt.indexOf("flushdb") > 0 || lineTxt.indexOf("FLUSHDB") > 0 || lineTxt.indexOf("flushall") > 0 || lineTxt.indexOf("FLUSHALL") > 0) {
                        System.out.println(lineTxt);
                    } else {
                        if (lineTxt.indexOf("del") > 0 || lineTxt.indexOf("DEL") > 0) {
                            //System.out.println("DEL:"+lineTxt);
                            j++;
                        }
                    }
                }
                System.out.println("del num = [" + j + "]");
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}

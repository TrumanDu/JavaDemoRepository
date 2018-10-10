package com.aibibang.demo.HDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/** 
* @author: Truman.P.Du 
* @since: 2016年9月6日 下午4:07:07 
* @version: v1.0
* @description:读写Hdfs文件
*/
public class OperationHdfsDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OperationHdfsDemo od = new OperationHdfsDemo();
		try {
			od.WriteHdfs("/user/truman/test.txt");
			od.ReadHdfs("/user/truman/test.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
   public void WriteHdfs(String path) throws IOException{
	   Configuration conf = new Configuration();
	   Path tempPath = new Path(path);
	   FileSystem fs = FileSystem.get(conf);
	   FSDataOutputStream out = fs.create(tempPath);
	   out.writeBytes("test write hdfs file!");
	   out.close();
	   fs.close();
	   System.out.println("hdfs文件创建成功！");
   }
   
   public void ReadHdfs(String path) throws IOException{
	   Configuration conf = new Configuration();
	   Path tempPath = new Path(path);
	   FileSystem fs = FileSystem.get(conf);
	   BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(tempPath),"utf-8"));
	   String line=null;
	   while((line=br.readLine())!=null){
		   System.out.println(line);
	   }
	   br.close();
	   fs.close();
	   System.out.println("hdfs文件读取成功！");
   }
}

package com.aibibang.demo.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/** 
* @author: Truman.P.Du 
* @since: 2016年3月16日 下午2:40:55 
* @version: v1.0
* @description:java io操作实例
*/
public class JavaIOExample {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
	  writeFile("d:/test.txt");	
	  randomFileHandle("d:/test.txt");
      readFile("d:/test.txt");
      
	}
	/**
	 * 根据路径读取文件内容
	 * @param path
	 * @throws FileNotFoundException 
	 */
    public static void readFile(String path) throws Exception{
    	FileInputStream in = new FileInputStream(path);
    	
    	InputStreamReader reader = new InputStreamReader(in, "UTF-8");
    	
    	StringBuffer sb = new StringBuffer();
    	
    	while(reader.ready()){
    		sb.append((char)reader.read());
    	}
    	System.out.println(sb.toString());
    	reader.close();
    	in.close();
    	//读完以后删除该文件
    	File file =new File(path);
    	file.delete();
    	
    }
    /**
     * 往文件中写入数据
     * @param path
     * @throws FileNotFoundException 
     */
	public static void writeFile(String path) throws Exception {
		File file =new File(path);
		
		FileOutputStream os = new FileOutputStream(path);

		OutputStreamWriter write = new OutputStreamWriter(os, "UTF-8");

		write.append("测试写内容！");

		write.append("\r\n");

		write.append("第二行");

		write.flush();

		write.close();
		
		os.close();

	}
	public static void randomFileHandle(String path) throws Exception{
		//效率较低，但可以随机读写。
		RandomAccessFile randomAccessFile = new RandomAccessFile(path, "rws");
        // 文件长度，字节数     
        long fileLength = randomAccessFile.length();     
        // 将写文件指针移到文件尾。     
        randomAccessFile.seek(fileLength); 
        //标识符+时间戳+key+value
        randomAccessFile.write("\r\n".getBytes()); 
        randomAccessFile.write("内容".getBytes()); 
        randomAccessFile.close();
	}
}

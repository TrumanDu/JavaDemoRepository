package com.aibibang.demo.HDFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/** 
* @author: Truman.P.Du 
* @since: 2016年9月7日 下午3:14:42 
* @version: v1.0
* @description:HDFS文件工具类
*/
public class HDFSUtil {
	private static Configuration conf;
	static {
		conf = new Configuration();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String path = "/user/truman/test2.txt";
		String path2 = "/user/truman/test.txt";
		HDFSUtil.writeHFile(path, "test write hdfs file!\nthis is second line!".getBytes());
		HDFSUtil.readHFile(path);
		HDFSUtil.writeHFile(path2, "test write hdfs file!".getBytes());
		//System.out.println(HDFSUtil.deleteHFile(path2,false));
		//System.out.println(HDFSUtil.deleteHFile(path));
		//System.out.println(HDFSUtil.deleteHFile(path2,true));
		System.out.println(HDFSUtil.uploadFile("D:\\logs\\mq.log", "/user/truman/mq.log"));
		HDFSUtil.readHFile("/user/truman/mq.log");
	    System.out.println(HDFSUtil.downFile("/user/truman/test2.txt", "D:\\logs\\test2.txt"));
		HDFSUtil.listHFile("/user/truman");*/
		System.out.println(HDFSUtil.uploadFile("E:\\Gitdemo\\gittestpro\\big-data-demo\\src\\main\\resources\\words.txt", "/user/truman/words.txt"));
	}

	/**
	 * 写文件
	 * @param pathName
	 * @param data
	 * @return
	 */
	public static boolean writeHFile(String pathName, byte[] data) {
		FileSystem fs = null;
		FSDataOutputStream out = null;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(pathName);
			out = fs.create(path);
			out.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				out.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
    /**
     * 读取HFile,输出
     * @param pathName
     */
	public static void readHFile(String pathName) {
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(pathName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(path), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除文件
	 * @param pathName
	 * @return
	 */
	public static boolean deleteHFile(String pathName){
		FileSystem fs = null;
		boolean result = false;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(pathName);
			result = fs.delete(path, false);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
    /**
     * 删除文件
     * @param pathName
     * @param recursive
     * @return
     */
	public static boolean deleteHFile(String pathName,boolean recursive){
		FileSystem fs = null;
		boolean result = false;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(pathName);
			result = fs.delete(path, recursive);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
    /**
     * 上传本地文件
     * @param pathName
     * @return
     */
	public static boolean uploadFile(String srcName,String dstName){
		FileSystem fs = null;
		boolean result = false;
		try {
			fs = FileSystem.get(conf);
			Path src = new Path(srcName);
			Path dst = new Path(dstName);
			fs.copyFromLocalFile(src, dst);
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
    /**
     * 下载文件
     * @param pathName
     * @return
     */
	public static boolean downFile(String srcName,String dstName){
		FileSystem fs = null;
		boolean result = false;
		try {
			fs = FileSystem.get(conf);
			Path src = new Path(srcName);
			Path dst = new Path(dstName);
			fs.copyToLocalFile(false,src, dst,true);
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 遍历文件夹
	 * @param pathName
	 */
	public static void listHFile(String pathName){
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			Path path = new Path(pathName);
			FileStatus[] fileStatuss = fs.listStatus(path);
			for(FileStatus fileStatus:fileStatuss){
				System.out.println(fileStatus.getPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

package com.aibibang.demo.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.util.Bytes;

/** 
* @author: Truman.P.Du 
* @since: 2016年3月16日 下午4:19:58 
* @version: v1.0
* @description:
*/
public class HbaseDemo {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception, ZooKeeperConnectionException, IOException {
		//操作表结构
		//HbaseUtil.createTable("truman", new String[]{"personal","professional"});
		//HbaseUtil.deleteTable("truman");
		//打印出所有的表
		HTableDescriptor[] tables = HbaseUtil.listTable();
		if (tables != null) {
			for (HTableDescriptor table : tables) {
				System.out.println(table.getNameAsString());
			}
		}
		//查看表是否存在
		//System.out.println(HbaseUtil.tableIsExists("truman"));

		//表数据操作
		HbaseUtil.addData("truman", Bytes.toBytes("row2"), Bytes.toBytes("personal"),
				Bytes.toBytes("name"), Bytes.toBytes("reason2"));
		HbaseUtil.addData("truman", Bytes.toBytes("row2"), Bytes.toBytes("personal"),
				Bytes.toBytes("city"), Bytes.toBytes("beijing"));
		
		HbaseUtil.addData("truman", Bytes.toBytes("row2"), Bytes.toBytes("professional"),
				Bytes.toBytes("designation"), Bytes.toBytes("test"));

		//获取数据
		System.out.println(HbaseUtil.getValue("truman", Bytes.toBytes("row2"), Bytes.toBytes("personal"), Bytes.toBytes("name")));

		//删除数据
		/*HbaseUtil.remove("truman", Bytes.toBytes("row2"),Bytes.toBytes("personal"), Bytes.toBytes("name"));
		HbaseUtil.remove("truman", Bytes.toBytes("row2"),null,null);*/
		//HbaseUtil.deleteTable("truman");
		System.out.println(HbaseUtil.getClusterStatus().getHBaseVersion());

	}
}

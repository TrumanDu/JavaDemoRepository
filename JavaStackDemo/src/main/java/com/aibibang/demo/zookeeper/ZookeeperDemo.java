package com.aibibang.demo.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/** 
* @author: Truman.P.Du 
* @since: 2016年12月16日 下午3:41:03 
* @version: v1.0
* @description:zookeeper api使用demo
* 原生API
*/
public class ZookeeperDemo {
	private static ZooKeeper zk;
	static {
		try {
			zk = new ZooKeeper("192.168.0.101:2181", 3000, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
                   System.out.println("检测到事件:"+event.getType());
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	
	public static void main(String[] args) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
       //创建目录
		zk.create("/trumantest", "trumanTestData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//创建子节点
		zk.create("/trumantest/child1", "child1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/trumantest/child2", "child2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//获取子节点
		System.out.println(zk.getChildren("/trumantest", false));
		//获取子节点数据
		System.out.println(new String(zk.getData("/trumantest/child1", false, null)));
		// 修改子目录节点数据
		 zk.setData("/trumantest/child2", "data for second...".getBytes(), -1);
		System.out.println(new String(zk.getData("/trumantest/child2", false, null)));
		// 删除子目录节点
		zk.delete("/trumantest/child2",-1); 
		System.out.println(zk.getChildren("/trumantest", false));
		
	}

}

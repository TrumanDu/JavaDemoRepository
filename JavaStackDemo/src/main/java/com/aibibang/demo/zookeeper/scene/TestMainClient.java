package com.aibibang.demo.zookeeper.scene;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/** 
* @author: Truman.P.Du 
* @since: 2016年12月16日 下午6:06:22 
* @version: v1.0
* @description:
*/
public class TestMainClient implements Watcher {
	public static ZooKeeper zk;
	
	public int sessionTimeout = 10000;
	public String root;

	public TestMainClient(String connectString) {
		if (zk == null) {
			try {
				zk = new ZooKeeper(connectString, sessionTimeout, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				zk = null;
			}
		}

	}

	@Override
	 public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("父："+event.getType());
	}
}

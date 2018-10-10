package com.aibibang.demo.zookeeper.scene;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/** 
* @author: Truman.P.Du 
* @since: 2016年12月16日 下午6:33:30 
* @version: v1.0
* @description:
*/
public class LeaderElection extends TestMainClient {
	public String leader;
	public String node;

	public LeaderElection(String connectString, String rootPath) {
		super(connectString);
		// TODO Auto-generated constructor stub
		this.root = rootPath;
		if (zk != null) {
			try {
				Stat s = zk.exists(rootPath, false);
				if (s == null) {
					zk.create(rootPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}

				//创建临时序号节点
				node = zk.create(rootPath + "/work", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void findLeader() {

		try {
			List<String> childs = zk.getChildren(root, true);
			Collections.sort(childs);
			leader = childs.get(0);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(leader);

	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if (event.getType() == Event.EventType.NodeChildrenChanged) {
			findLeader();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeaderElection le = new LeaderElection("192.168.0.101:2181", "/trumantest");
		le.findLeader();
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

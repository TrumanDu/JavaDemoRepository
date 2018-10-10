package com.aibibang.demo.zookeeper.scene;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/** 
* @author: Truman.P.Du 
* @since: 2017年1月17日 下午3:56:05 
* @version: v1.0
* @description:
*/
public class Test extends TestMainClient {

	public Test(String connectString,String rootPath) {
		super(connectString);
		this.root = rootPath;
		if (zk != null) {
			try {
				Stat s = zk.exists(rootPath, true);
				if (s == null) {
					String path =zk.create(rootPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					zk.getData(path, this, null);
					
					
				}
				
				/*String path2 =zk.create("/test", null, Ids.OPEN_ACL_UNSAFE,  CreateMode.EPHEMERAL);
				zk.exists(path2, true);*/
				
				String path3 =zk.create(rootPath+"/test3", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				zk.getData(path3, this, null);
				
				zk.exists(path3, true);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
           Test test = new Test("192.168.0.101:2181", "/trumantest");
           try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

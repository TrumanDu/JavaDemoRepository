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
* @since: 2016年12月20日 下午4:57:33 
* @version: v1.0
* @description:分布式锁实现
*/
public class Locks extends TestMainClient {
	String selfNode;
	String min;

	public Locks(String connectString, String rootPath) {
		super(connectString);
		this.root = rootPath;
		if (zk != null) {
			try {
				Stat s = zk.exists(rootPath, false);
				if (s == null) {
					zk.create(rootPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}

				//创建临时序号节点
				selfNode = zk.create(rootPath + "/work", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void getLock() {
		try {
			List<String> childs = zk.getChildren(root, false);
			Collections.sort(childs);//按字典顺序排序
			min = root + "/" + childs.get(0);
			if (selfNode != null && selfNode.equals(min)) {
				doAction();
			} else {
				doWaitForLock();
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doAction() {
		System.out.println("获得锁。。。。。");
		try {
			System.out.println(Thread.currentThread().getName() + ":开始工作。。。。。");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("释放锁。。。。。");
	}

	public void doWaitForLock() {
		System.out.println("等待获取锁。。。。。");
		try {
			zk.exists(min, true);
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + ":阻塞。。。。。");
				this.wait();
			}
			getLock();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == Event.EventType.NodeDeleted) {
			System.out.println("接受到通知");
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + ":通知取消阻塞");
				this.notify();
			}

		}
	}

	public static void main(String[] args) {
		/*new Thread() {
			public void run() {
				Locks lock = new Locks("192.168.0.101:2181", "/trumantest");
				lock.getLock();
			};
		}.start();*/
		Locks lock = new Locks("192.168.0.101:2181", "/trumantest");
		lock.getLock();	

	}

}

package com.aibibang.demo.zookeeper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

/**
 * @authTruman.P.Du
 * @since: 2016年1月26日 下午3:14:48
 * @version: v1.0
 * @description:curator操作zookeeper
 */
public class ZKCuratorTest {
	public static void main(String[] args) throws Exception {
		 //testCreate();
		testSupervisorListener();
	}

	public static void testCreate() throws Exception {
		String host = "192.168.0.101:2181";
		Builder builder = CuratorFrameworkFactory.builder();
		CuratorFramework client = builder
				.connectString(host)
				.sessionTimeoutMs(30000)
				.connectionTimeoutMs(30000)
				.canBeReadOnly(false)
				.retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
				// .namespace("storm")
				.defaultData(null).build();
		client.start();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		/**
	     * 监听子节点的变化情况
	     */
	    final PathChildrenCache childrenCache = new PathChildrenCache(client, "/dutest", true);
	    childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
	    childrenCache.getListenable().addListener(
	      new PathChildrenCacheListener() {
	        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
	            throws Exception {
	            switch (event.getType()) {
	            case CHILD_ADDED:
	              System.out.println("CHILD_ADDED: " + event.getData().getPath());
	              break;
	            case CHILD_REMOVED:
	              System.out.println("CHILD_REMOVED: " + event.getData().getPath());
	              break;
	            case CHILD_UPDATED:
	              System.out.println("CHILD_UPDATED: " + event.getData().getPath());
	              break;
	            default:
	              break;
	          }
	        }
	      },
	      pool
	    );
		client.delete().inBackground().forPath("/dutest/c");
		client.create().forPath("/dutest/c",("test" + System.lineSeparator() + "line").getBytes());
/*		client.close();
		client = builder
				.connectString(host)
				.sessionTimeoutMs(30000)
				.connectionTimeoutMs(30000)
				.canBeReadOnly(false)
				.retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
				// .namespace("storm")
				.defaultData(null).build();
		client.start();
		System.out.println("+++++++++++++++++++++++++++++"+new String(client.getData().forPath("/dutest")));*/
	    Thread.sleep(100 * 1000);
	    pool.shutdown();
	    client.close();
	}

	public static void testSupervisorListener() throws Exception {
		String host = "192.168.0.101:2181";
		Map<String,String>supervisorsHost = new HashMap<String,String>();
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(host)
				.sessionTimeoutMs(30000)
				.connectionTimeoutMs(30000)
				.canBeReadOnly(false)
				.retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
				// .namespace("storm")
				.defaultData(null).build();
		client.start();
		List<String> nodeList = client.getChildren().forPath("/storm/supervisors");
		for (String node : nodeList) {
			System.out.println("-----------------------------" + node);
			String result = new String(client.getData().watched().forPath("/storm/supervisors/" + node), "utf-8");
			int startIndex = result.indexOf(node + "t");
			String newStr = result.substring(startIndex, result.length());
			int lastIndex = newStr.indexOf("sr");
			String hostname = newStr.substring(node.length()+1, lastIndex).trim();
			System.out.println("+++++++++++++++++++++++++++++" +hostname);
			supervisorsHost.put(node, hostname);
		}
		ExecutorService pool = Executors.newFixedThreadPool(1);
	    /**
	     * 监听数据节点的变化情况
	     */
/*	    final NodeCache nodeCache = new NodeCache(client, "/storm/supervisors", false);
	    nodeCache.start(true);
	    nodeCache.getListenable().addListener(
	      new NodeCacheListener() {
	        @Override
	        public void nodeChanged() throws Exception {
	          System.out.println("Node data is changed, new data: " + 
	            new String(nodeCache.getCurrentData().getData()));
	        }
	      }, 
	      pool);*/
		// client.getChildren().watched().inBackground().forPath("/storm/supervisor");
		// 结束使用
		// client.close();
	    /**
	     * 监听子节点的变化情况
	     */
	    final PathChildrenCache childrenCache = new PathChildrenCache(client, "/storm/supervisors", true);
	    final Map<String,String> superMap = supervisorsHost;
	    childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
	    childrenCache.getListenable().addListener(
	      new PathChildrenCacheListener() {
	        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
	            throws Exception {
	        	String eventPath;
	        	String node;
	            switch (event.getType()) {
	            case CHILD_ADDED:
	              System.out.println("CHILD_ADDED: " + event.getData().getPath());
	              break;
	            case CHILD_REMOVED:
	              eventPath = event.getData().getPath();
	              node = ZKPaths.getNodeFromPath(event.getData().getPath());
	              System.out.println("CHILD_REMOVED: " + eventPath+" hostname:"+superMap.get(node));
	              break;
	            case CHILD_UPDATED:
	              eventPath = event.getData().getPath();	
	              node = ZKPaths.getNodeFromPath(event.getData().getPath());
	              System.out.println("CHILD_UPDATED: " + eventPath+" hostname:"+superMap.get(node));
	              System.out.println();
	              break;
	            default:
	              break;
	          }
	        }
	      },
	      pool
	    );
	    Thread.sleep(Long.MAX_VALUE);
	    pool.shutdown();
	    client.close();
	  }
}

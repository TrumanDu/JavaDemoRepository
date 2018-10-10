package com.aibibang.demo.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.JedisClusterCRC16;
import redis.clients.util.Slowlog;

/**
 * 
 * @author Truman.P.Du
 *
 */
public class RedisUtil {
	public JedisCluster jc = null;

	public RedisUtil(String host, int port) {
		HostAndPort hostAndPort = new HostAndPort(host, port);
		jc = new JedisCluster(hostAndPort);
	}

	/**
	 * ClusterNodes master
	 * 
	 * @return
	 */
	public Map<String, JedisPool> ClusterNodes() {
		Map<String, JedisPool> nodes = jc.getClusterNodes();
		return nodes;
	}

	/**
	 * 获取该RedisCluster所有节点IP信息
	 * 
	 * @return
	 */
	public Map<String, String> ClusterNodesIP() {
		Map<String, JedisPool> nodes = jc.getClusterNodes();
		Map<String, String> clusterNodesIP = new HashMap<>();
		nodes.forEach((k, v) -> {
			String host = k.split(":")[0];
			clusterNodesIP.put(host, host);
		});
		return clusterNodesIP;
	}

	/**
	 * 获取该RedisCluster所有Master对应slave 信息 <k,v> k=master ip:port v=[{slave
	 * ip:port},{slave ip:port}]
	 * 
	 * @return
	 */
	public Map<String, List<String>> ClusterNodesMap() {
		Map<String, JedisPool> nodes = jc.getClusterNodes();
		String nodesStr = "";
		for (String key : nodes.keySet()) {
			JedisPool jedisPool = nodes.get(key);
			Jedis jedis = jedisPool.getResource();
			nodesStr = jedis.clusterNodes();
			jedis.close();
			break;
		}

		String[] nodesArray = nodesStr.split("\n");
		List<RedisNode> redisNodes = new ArrayList<>();
		Map<String, List<String>> clusterNodes = new HashMap<>();
		Map<String, String> temp = new HashMap<>();
		for (String node : nodesArray) {
			if (node.indexOf("fail") > 0)
				continue;
			RedisNode redisNode = new RedisNode();
			String[] detail = node.split(" ");
			if (node.contains("master")) {
				temp.put(detail[0], detail[1]);
				redisNode.setRole(0);
				redisNode.setPid("0");
			} else {
				redisNode.setRole(1);
				redisNode.setPid(detail[3]);
			}

			redisNode.setHostAndPort(detail[1]);
			redisNode.setId(detail[0]);
			redisNodes.add(redisNode);

		}

		for (RedisNode node : redisNodes) {
			if (node.getRole() == 0)
				continue;
			if (temp.containsKey(node.getPid())) {
				String key = temp.get(node.getPid());
				if (clusterNodes.containsKey(key)) {
					List<String> slaves = clusterNodes.get(key);
					slaves.add(node.getHostAndPort());
					clusterNodes.put(key, slaves);
				} else {
					List<String> slaves = new ArrayList<>();
					slaves.add(node.getHostAndPort());
					clusterNodes.put(key, slaves);
				}
			}
		}

		return clusterNodes;
	}

	/**
	 * 获取cluster 所有节点 slowlog 信息
	 * 
	 * @return
	 */
	public Map<String, List<Slowlog>> slowlogs() {
		Map<String, JedisPool> nodesMap = this.ClusterNodes();
		Map<String, List<Slowlog>> mapSlowlog = new HashMap<>();
		// 获取所有master 中的slowlog
		for (String node : nodesMap.keySet()) {
			JedisPool jedisPool = nodesMap.get(node);
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				List<Slowlog> slowlog = jedis.slowlogGet(128);
				mapSlowlog.put(node, slowlog);
			} catch (JedisConnectionException e) {
				String host = node.split(":")[0];
				String port = node.split(":")[1];
				jedis = new Jedis(host, Integer.parseInt(port));
				List<Slowlog> slowlog = jedis.slowlogGet();
				mapSlowlog.put(node, slowlog);
			} finally {
				if (jedis != null)
					jedis.close();
			}
		}

		// 获取slave 中的slowlog
		Map<String, List<String>> clusterNodes = ClusterNodesMap();
		for (String marter : clusterNodes.keySet()) {
			List<String> slaves = clusterNodes.get(marter);
			for (String slave : slaves) {
				Jedis jedis = null;
				try {
					String host = slave.split(":")[0];
					String port = slave.split(":")[1];
					jedis = new Jedis(host, Integer.parseInt(port));
					List<Slowlog> slowlog = jedis.slowlogGet(128);
					mapSlowlog.put(slave, slowlog);
				} catch (JedisConnectionException e) {
					e.printStackTrace();
				} finally {
					if (jedis != null)
						jedis.close();
				}
			}
		}
		return mapSlowlog;
	}

	/**
	 * 获取cluster 所有节点 client 信息
	 * 
	 * @return
	 */
	public Map<String, String> clientList() {
		Map<String, JedisPool> nodesMap = this.ClusterNodes();
		Map<String, String> mapClient = new HashMap<>();
		for (String node : nodesMap.keySet()) {
			JedisPool jedisPool = nodesMap.get(node);
			Jedis jedis = jedisPool.getResource();
			mapClient.put(node, jedis.clientList());
			jedis.close();
		}

		return mapClient;
	}

	/**
	 * 计算key所在slot
	 * 
	 * @param key
	 * @return
	 */
	public static int getSlotByKey(String key) {
		return JedisClusterCRC16.getSlot(key);
	}

	class RedisNode {
		private String id;
		private String pid;
		private String hostAndPort;
		// 0:master 1:slave
		private int role;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getHostAndPort() {
			return hostAndPort;
		}

		public void setHostAndPort(String hostAndPort) {
			this.hostAndPort = hostAndPort;
		}

		public int getRole() {
			return role;
		}

		public void setRole(int role) {
			this.role = role;
		}
	}

	public void closeCluster() {
		if (jc != null) {
			try {
				jc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

	}
}

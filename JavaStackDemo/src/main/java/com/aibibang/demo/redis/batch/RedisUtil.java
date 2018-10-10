package com.aibibang.demo.redis.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.JedisClusterCRC16;
/**
 * @author Truman.P.Du
 * @since 2015年12月24日09:02:03
 * @version v1.0
 * redisCluster key批量操作实现
 */
public class RedisUtil {
	//private static final Logger logger = Logger.getLogger(RedisUtil.class);
	public static JedisCluster jc = null;
	private static Map<String, JedisPool> nodeMap = null;
	private static TreeMap<Long, String> slotHostMap = null;
	private static ResourceBundle bundle = ResourceBundle.getBundle("config");
	static {
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		HostAndPort hostAndPort = null;
		String connectStrs = bundle.getString("redis.connect");
		String[] connectArray = connectStrs.split(",", -1);
		for (String connect : connectArray) {
			String host = connect.split(":")[0].trim();
			String port = connect.split(":")[1].trim();
			hostAndPort = new HostAndPort(host, Integer.parseInt(port));
			jedisClusterNode.add(hostAndPort);
		}
		jc = new JedisCluster(jedisClusterNode);
		
		nodeMap = jc.getClusterNodes();

		slotHostMap = getSlotHostMap(connectArray[0]);
	}

	public static void set(String key, String value, int retries) {
		try {
			jc.set(key, value);
		} catch (Exception e) {
			try {
				Thread.sleep(5000);
				set(key, value, --retries);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public  String get(String key, int retries) {
		try {
			return jc.get(key);
		} catch (Exception e) {
			try {
				Thread.sleep(5000);
				return get(key, --retries);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 批量获取set类型数据
	 * @param keys
	 * @return
	 */
	public static Map<String, Set<String>> batchGetSetData(List<String> keys) {
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		Map<JedisPool, List<String>> poolKeysMap = getPoolKeyMap(keys);
		Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();
		for (Map.Entry<JedisPool, List<String>> entry : poolKeysMap.entrySet()) {
			JedisPool jedisPool = entry.getKey();
			List<String> subkeys = entry.getValue();
			if (subkeys == null || subkeys.isEmpty()) {
				continue;
			}
			//申请jedis对象
			Jedis jedis = null;
			Pipeline pipeline = null;
			List<Object> subResultList = null;
			try {
				jedis = jedisPool.getResource();
				pipeline = jedis.pipelined();

				for (String key : subkeys) {
					pipeline.smembers(key);
				}

				subResultList = pipeline.syncAndReturnAll();
			} catch (JedisConnectionException e) {
				e.getMessage();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				if (pipeline != null)
					try {
						pipeline.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//释放jedis对象
				if (jedis != null) {
					jedis.close();
				}
			}
			if (subResultList == null || subResultList.isEmpty()) {
				continue;
			}
			if (subResultList.size() == subkeys.size()) {
				for (int i = 0; i < subkeys.size(); i++) {
					String key = subkeys.get(i);
					Object result = subResultList.get(i);
					/*if(result.){
						
					}else{
						
					}*/
					resultMap.put(key, (Set<String>) result);
				}
			} else {
				System.out.println("redis cluster pipeline error!");
			}
		}
		return resultMap;
	}

	/**
	 * 将key按slort分批整理
	 * @param keys
	 * @return
	 */
	private static Map<JedisPool, List<String>> getPoolKeyMap(List<String> keys) {
		Map<JedisPool, List<String>> poolKeysMap = new LinkedHashMap<JedisPool, List<String>>();
		try {
			for (String key : keys) {

				int slot = JedisClusterCRC16.getSlot(key);

				//获取到对应的Jedis对象
				Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot+1));

				JedisPool jedisPool = nodeMap.get(entry.getValue());

				if (poolKeysMap.containsKey(jedisPool)) {
					poolKeysMap.get(jedisPool).add(key);
				} else {
					List<String> subKeyList = new ArrayList<String>();
					subKeyList.add(key);
					poolKeysMap.put(jedisPool, subKeyList);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return poolKeysMap;
	}

	/**
	 * slort对应node
	 * @param anyHostAndPortStr
	 * @return
	 */
	private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
		TreeMap<Long, String> tree = new TreeMap<Long, String>();
		String parts[] = anyHostAndPortStr.split(":");
		HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
		try {
			Jedis jedisNode = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
			List<Object> list = jedisNode.clusterSlots();
			for (Object object : list) {
				List<Object> list1 = (List<Object>) object;
				List<Object> master = (List<Object>) list1.get(2);
				String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
				tree.put((Long) list1.get(0), hostAndPort);
				tree.put((Long) list1.get(1), hostAndPort);
			}
			jedisNode.close();
		} catch (Exception e) {

		}
		return tree;
	}
	
    /**
     * 计算key所在slot
     * @param key
     * @return
     */
	public static int getSlotByKey(String key) {
		return JedisClusterCRC16.getSlot(key);
	}
	
	public void closeCluster(){
		//jc.close();
	}
	public static void main(String[] args) throws Exception {
		RedisUtil.set("test", "test", 1);
	}
}
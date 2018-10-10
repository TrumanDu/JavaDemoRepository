package com.aibibang.demo.storm;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月12日 上午10:50:52 
* @version: v1.0
* @description:
*/
public class WordCounter implements IRichBolt {
	Integer id;
	String name;
	Map<String, Integer> counters;
	private OutputCollector collector;

	public void cleanup() {
		// TODO Auto-generated method stub
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("WordCounter.cleanup()");
	}

	public void execute(Tuple input) {
		System.out.println("WordCounter.execute()");
		String str = input.getString(0);
		/**
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		//对元组作为应答
		collector.ack(input);

	}

	public void prepare(Map arg0, TopologyContext context, OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();

	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		System.out.println("WordCounter.declareOutputFields()");
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		System.out.println("WordCounter.getComponentConfiguration()");
		return null;
	}

}

package com.aibibang.demo.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月12日 上午10:39:20 
* @version: v1.0
* @description:
*/
public class WordNormalizer implements IRichBolt {
	private static final long serialVersionUID = 3644849073824009317L;
    private OutputCollector collector ;
	public void cleanup() {
		// TODO Auto-generated method stub
		System. out.println("WordNormalizer.cleanup()" );   
	}

	/**
	 * *bolt*从单词文件接收到文本行，并标准化它。
	 * 文本行会全部转化成小写，并切分它，从中得到所有单词。
	*/
	public void execute(Tuple input) {
		System.out.println("WordNormalizer.execute()");
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for (String word : words) {
			word = word.trim();
			if (!word.isEmpty()) {
				word = word.toLowerCase();
				/*//发布这个单词*/
				collector.emit(input, new Values(word));
			}
		}
		//对元组做出应答
		collector.ack(input);
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		// TODO Auto-generated method stub
		System. out.println("WordNormalizer.prepare()" );
		 this. collector = arg2 ;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		 System. out.println("WordNormalizer.declareOutputFields()" );
		arg0.declare(new Fields("word"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		System. out.println("WordNormalizer.getComponentConfiguration()" );
		return null;
	}

}

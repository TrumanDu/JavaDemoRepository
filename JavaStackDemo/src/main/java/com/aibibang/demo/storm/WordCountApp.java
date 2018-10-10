package com.aibibang.demo.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月12日 上午10:54:13 
* @version: v1.0
* @description:storm 字数统计 topology app
*/
public class WordCountApp {

	public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
		// TODO Auto-generated method stub

		//定义拓扑	
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("word-reader", new WordReader());

		builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");

		builder.setBolt("word-counter", new WordCounter()).fieldsGrouping("word-normalizer", new Fields("word"));

		StormTopology topology = builder.createTopology();

		Config conf = new Config();

		String fileName = "words.txt";

		conf.put("fileName", fileName);

		conf.setDebug(false);

		//运行拓扑

		//提交top
		if (args != null && args.length > 0) { //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
			StormSubmitter.submitTopology(args[0], conf, topology);
		} else {//没有参数时，本地提交
			LocalCluster localCluster = new LocalCluster();
			
			localCluster.submitTopology("wordcountapp", conf, topology);
			
			Thread.sleep(10000);
			
			localCluster.shutdown();
		}

	}

}

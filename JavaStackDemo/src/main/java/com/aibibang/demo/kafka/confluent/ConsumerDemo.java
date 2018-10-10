package com.aibibang.demo.kafka.confluent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;


/** 
* @author: Truman.P.Du 
* @since: 2017年2月14日 下午2:58:04 
* @version: v1.0
* @description:
*/
public class ConsumerDemo {
	public static final String TOPIC = "ConfluentTestNew";
	private static final Logger log = Logger.getLogger(ConsumerDemo.class);
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Properties config = new Properties();
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		config.put("group.id", "test11");
		config.put("bootstrap.servers", "192.168.0.101:9092");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//config.put("enable.auto.commit", "false");
		KafkaConsumer<String, String>consumer = new KafkaConsumer<String, String>(config);
		
		
		List<PartitionInfo> partitionInfos = consumer.partitionsFor(TOPIC);
		//consumer.subscribe(Arrays.asList(TOPIC));
		List<TopicPartition>topicPartitions = new ArrayList<TopicPartition>();
		for(PartitionInfo partitionInfo:partitionInfos){
			TopicPartition partition = new TopicPartition(TOPIC, partitionInfo.partition());
			topicPartitions.add(partition);
		}
	    
	    //TopicPartition partition1 = new TopicPartition(TOPIC, 1);
	     consumer.assign(topicPartitions);
		while (true) {
			  ConsumerRecords<String, String> records = (ConsumerRecords<String, String>) consumer.poll(100);
			  for (ConsumerRecord<String, String> record : records){
				  log.debug("consumer message: key ="+record.key()+" value:"+record.value());
			   }
			   //consumer.commitSync();
			}
	}

}

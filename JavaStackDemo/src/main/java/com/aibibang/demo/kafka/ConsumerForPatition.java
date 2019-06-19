package com.aibibang.demo.kafka;

import java.net.InetAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

/**
 * @author Truman.P.Du
 * @date 2019年4月11日 下午1:34:04
 * @version 1.0
 */
public class ConsumerForPatition {
	public static final String TOPIC = "truman_test_offset";

	public static void main(String[] args) throws Exception {
		Properties config = new Properties();

		config.put("group.id", "test11");
		config.put("bootstrap.servers", "192.168.0.101:8092");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put(ConsumerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostAddress());
		
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(config);
		List<PartitionInfo> partitionInfos = consumer.partitionsFor(TOPIC);
		List<TopicPartition> topicPartitions = new ArrayList<TopicPartition>();
		for (PartitionInfo partitionInfo : partitionInfos) {
			TopicPartition partition = new TopicPartition(TOPIC, partitionInfo.partition());
			topicPartitions.add(partition);
		}
		consumer.assign(topicPartitions);
		boolean isConsumer = true;
		while (isConsumer) {
			ConsumerRecords<String, String> records = (ConsumerRecords<String, String>) consumer
					.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("consumer message: key =" + record.key() + " value:" + record.value());
			}
			// consumer.commitSync();
		}
		consumer.close();
	}
}

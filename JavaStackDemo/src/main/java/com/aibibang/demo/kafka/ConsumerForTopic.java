package com.aibibang.demo.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Truman.P.Du
 * @date 2019年4月11日 下午1:33:48
 * @version 1.0
 */
public class ConsumerForTopic {
	public static final String TOPIC = "truman_test_connect";

	public static void main(String[] args) throws Exception {
		Properties config = new Properties();

		config.put("group.id", "test11");
		config.put("bootstrap.servers", "192.168.0.101:8092");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//config.put(ConsumerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostAddress());
		
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(config);
		consumer.subscribe(Arrays.asList(TOPIC));
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

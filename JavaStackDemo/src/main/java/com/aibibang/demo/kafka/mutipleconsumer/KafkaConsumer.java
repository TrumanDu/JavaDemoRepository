package com.aibibang.demo.kafka.mutipleconsumer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author：Truman.P.Du
 * @createDate: 2018年1月25日 下午4:15:53
 * @version:1.0
 * @description:mutiple consumer
 */
public class KafkaConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
	private List<ConsumerThread> consumers =new ArrayList<ConsumerThread>();

	public KafkaConsumer(String brokers, String topic, String group, int numberOfConsumer,
			CustomerDealMsg messageHandle) {
		for (int i = 0; i < numberOfConsumer; i++) {
			consumers.add(new ConsumerThread(brokers, topic, group, messageHandle));
		}
		execute();
	}

	public void execute() {
		for (ConsumerThread consumerThread : consumers) {
			Thread thread = new Thread(consumerThread);
			thread.start();
		}
	}

	public interface CustomerDealMsg {
		public boolean onMessage(ConsumerRecord<String, String> record) throws Exception;
	}

	public class ConsumerThread implements Runnable {
		private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
		private CustomerDealMsg messageHandle;
		private String topic;

		public ConsumerThread(String brokers, String topic, String group, CustomerDealMsg messageHandle) {
			this.topic = topic;
			consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(
					createConsumerConfig(brokers, group));
			consumer.subscribe(Arrays.asList(topic));
			this.messageHandle = messageHandle;
		}

		private Properties createConsumerConfig(String brokers, String group) {
			Properties props = new Properties();
			props.put("bootstrap.servers", brokers);
			props.put("group.id", group);
			props.put("enable.auto.commit", "false");
			props.put("auto.offset.reset", "earliest");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("max.poll.records", "100");
			props.put("client.id", getHostName());
			return props;
		}

		@Override
		public void run() {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				try {
					for (ConsumerRecord<String, String> record : records) {
						while (!messageHandle.onMessage(record)) {
                            LOGGER.warn(String.format("topic:%s, group:%s, partition:%d, offset:%d",
                                    topic,  record.partition(), record.offset()));
                            Thread.sleep(10000);
                        }
						consumer.commitSync(Collections.singletonMap(new TopicPartition(topic,record.partition()), new OffsetAndMetadata(record.offset() + 1)));
					}
					
				} catch (Exception e) {
					LOGGER.error("ConsumerThread  processing message have  errors", e);
				}
			}

		}

	}

	public static void main(String[] args) {

		CustomerDealMsg customerDealMsg = new CustomerDealMsg() {
			@Override
			public boolean onMessage(ConsumerRecord<String, String> record) throws Exception {
				// LOGGER.info("offset = {}, key = {}, value = {}", record.offset(),
				// record.key(), record.value());
				System.out.println(record.value()+"|patition:"+record.partition());
				return true;
			}
		};
		String brokers = "101.168.238.101:8092";
		try {
			KafkaConsumer consumer = new KafkaConsumer(brokers, "test_es_to_kafka", "truman-user101", 1, customerDealMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static String getHostNameForLiunx() {
		try {
			return (InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException uhe) {
			String host = uhe.getMessage(); // host = "hostname: hostname"
			if (host != null) {
				int colon = host.indexOf(':');
				if (colon > 0) {
					return host.substring(0, colon);
				}
			}
			return "UnknownHost";
		}
	}

	public static String getHostName() {
		if (System.getenv("COMPUTERNAME") != null) {
			return System.getenv("COMPUTERNAME");
		} else {
			return getHostNameForLiunx();
		}
	}
}

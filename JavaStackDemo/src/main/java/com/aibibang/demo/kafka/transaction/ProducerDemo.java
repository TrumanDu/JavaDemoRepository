package com.aibibang.demo.kafka.transaction;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author Truman.P.Du
 * @date Jul 6, 2019 1:50:15 PM
 * @version 1.0 支持事务
 */
public class ProducerDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, "3");
		props.put(ProducerConfig.LINGER_MS_CONFIG, "10");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional_id-0");
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);
		kafkaProducer.initTransactions();
		kafkaProducer.beginTransaction();
		for (int i = 0; i < 10; i++) {
			kafkaProducer.send(new ProducerRecord<String, String>("truman_kafka_center",1, "key"+i, "hello world.")).get();
		}
		
		kafkaProducer.commitTransaction();
		kafkaProducer.close();

	}

}

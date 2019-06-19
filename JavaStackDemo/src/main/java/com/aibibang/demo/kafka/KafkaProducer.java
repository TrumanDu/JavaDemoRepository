package com.aibibang.demo.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author：Truman.P.Du
 * @createDate: 2017年11月9日 上午10:24:02
 * @version:1.0
 * @description:
 */
public class KafkaProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
	private org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

	public KafkaProducer(String brokers) {
		if (brokers == null) {
			throw new KafkaException("please check you attr!");
		}

		producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(createProducerConfig(brokers));
	}

	// 异步
	public void sendMessage(String topic, String key, String message) {
		producer.send(new ProducerRecord<String, String>(topic, key, message), new Callback() {
			@Override
			public void onCompletion(RecordMetadata arg0, Exception e) {
				if (e != null) {
					LOGGER.error("producer send topic:{} key:{} message:{}  fail.", topic, key, message, e);
				}

			}
		});
	}

	private Properties createProducerConfig(String brokers) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokers);
		props.put("acks", "1");
		props.put("retries", 3);
        //props.put("batch.size", 500);
		props.put("linger.ms", 10);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return props;
	}

	public void close() {
		if (producer != null) {
			producer.close();
		}
	}

	public static void main(String[] args) throws Exception {
		KafkaProducer kafkaProducer = new KafkaProducer("192.168.0.101:8092");
		String value = "";
		
		
		for (int i = 0; i < 10000000; i++) {
			JSONObject json = new JSONObject();
			if (i % 2 == 0) {
                value="even";
			} else {
				value="odd";
			}
			json.put("name", value);
			json.put("number", i);
			System.out.println(json.toString());
			kafkaProducer.sendMessage("truman_test_connect", "" + i, json.toJSONString());
			Thread.sleep(1000*60*10);
		}

	}

}

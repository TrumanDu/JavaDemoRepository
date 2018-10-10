package com.aibibang.demo.kafka.singleconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author：Truman.P.Du
 * @createDate: 2017年11月2日 下午4:12:01
 * @version:1.0
 * @description:
 * 
 * 				该工具类采用Single consumer, multiple worker processing threads
 */
public class KafkaConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
	private String topic;
	private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
	private CustomerDealMsg customerDealMsg;
	private ExecutorService pool;

	public KafkaConsumer(String brokers, String group, String topic, CustomerDealMsg customerDealMsg) throws Exception {
		if (brokers == null || group == null || customerDealMsg == null) {
			throw new KafkaException("please check you args!");
		}
		this.topic = topic;
		this.customerDealMsg = customerDealMsg;
		consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(
				createConsumerConfig(brokers, group));
	}

	public void execute(int numberOfThreads) throws Exception {
		pool = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			//poll拉去最后一次消费message 开始起的信息，即使不提交offset，也不会重新拉去该消息，
			ConsumerRecords<String, String> records = consumer.poll(100);
			try {
				List<Future<Boolean>> results = new ArrayList<Future<Boolean>>();
				for (ConsumerRecord<String, String> record : records) {
					Future<Boolean> future = pool.submit(new ConsumerThreadHandler(record, customerDealMsg));
					results.add(future);
				}

				boolean isAllDeal = true;
				if (results.size() <= 0)
					continue;
				for (Future<Boolean> future : results) {
					Boolean result = future.get();
					if (result != null && result.booleanValue())
						continue;

					isAllDeal = false;
					break;
				}
				// 所有记录处理成功后，提交offset
				if (isAllDeal) {
					consumer.commitAsync();
				}
			} catch (Exception e) {
				LOGGER.error("multiple worker processing threads have  errors", e);
			}
		}
	}

	private Properties createConsumerConfig(String brokers, String group) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokers);
		props.put("group.id", group);
		props.put("auto.commit.enable", "false");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("rebalance.backoff.ms", "2000");
		props.put("rebalance.max.retries", "5");
		props.put("max.poll.records", "1000");
		return props;
	}

	public class ConsumerThreadHandler implements Callable<Boolean> {
		private ConsumerRecord<String, String> consumerRecord;
		private CustomerDealMsg customerDealMsg;

		public ConsumerThreadHandler(ConsumerRecord<String, String> consumerRecord, CustomerDealMsg customerDealMsg) {
			this.consumerRecord = consumerRecord;
			this.customerDealMsg = customerDealMsg;
		}

		public Boolean call() throws Exception {
			return customerDealMsg.onMessage(consumerRecord);
		}
	}

	public interface CustomerDealMsg {
		public Boolean onMessage(ConsumerRecord<String, String> record) throws Exception;
	}

	public static void main(String[] args) {
		
		CustomerDealMsg customerDealMsg = new CustomerDealMsg() {
			@Override
			public Boolean onMessage(ConsumerRecord<String, String> record) throws Exception {
				//LOGGER.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
				System.out.println(record.value());
				return Boolean.TRUE;
			}
		};
		String brokers = "192.168.0.101:8092";
		try {
			KafkaConsumer consumer = new KafkaConsumer(brokers, "topicgroup", "topic", customerDealMsg);
			consumer.execute(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

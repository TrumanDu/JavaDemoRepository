package com.aibibang.demo.kafka.confluent;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;


/** 
* @author: Truman.P.Du 
* @since: 2017年2月14日 下午2:18:08 
* @version: v1.0
* @description:confluent 生产者 demo
*/
public class ProducerDemo {
    
	public static final String TOPIC = "ConfluentTestNew";
	public static int MESSAGE_NUM = 60*60*2;
	private static final Logger log = Logger.getLogger(ProducerDemo.class);
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		//config.put("client.id", InetAddress.getLocalHost().getHostName());
		 props.put("bootstrap.servers", "192.168.0.101:9092");
		 props.put("acks", "all");
		 props.put("retries", 0);
		 props.put("batch.size", 16384);
		 props.put("linger.ms", 1);
		 props.put("buffer.memory", 33554432);
		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 Producer<String, String> producer = new KafkaProducer<String, String>(props);
		
		for(int i=0;i<MESSAGE_NUM;i++){
			try {
	
				final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, Integer.toString(i), Integer.toString(i));
				log.debug("send message："+i);
				Future<RecordMetadata> future = producer.send(record,new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						// TODO Auto-generated method stub
						if (exception != null){
						      log.debug("Send failed for record {}"+record, exception);
						  }
					}
				});
				
				try {
					RecordMetadata metadata = future.get();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		producer.close();
		
	}

}

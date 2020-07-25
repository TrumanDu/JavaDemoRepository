package com.aibibang.demo.kafka.admin;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;

/**
 * @author Truman.P.Du
 * @date Jul 18, 2019 2:50:40 PM
 * @version 1.0
 */
public class KafkaAdminDemo {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

		KafkaAdminClient adminClient = (KafkaAdminClient) AdminClient.create(props);
		String topicName = "test_delete_topic";
		int testNum = 500;
		int testIndex = 0;

		for (int i = testIndex; i < testNum; i++) {
			try {
				NewTopic topic = new NewTopic(topicName + "_" + i, 10, (short) 2);
				adminClient.createTopics(Collections.singleton(topic)).all().get();
				System.out.println(topicName + "_" + i + ":"
						+ adminClient.listTopics().names().get().contains(topicName + "_" + i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = testIndex; i < testNum; i++) {
			try {
				if (adminClient.listTopics().names().get().contains(topicName + "_" + i)) {
					System.out.println("删除："+topicName + "_" + i);
					adminClient.deleteTopics(Collections.singleton(topicName + "_" + i)).all().get();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = testIndex; i < testNum; i++) {
			try {
				if (adminClient.listTopics().names().get().contains(topicName + "_" + i)) {
					System.out.println("未删除："+topicName + "_" + i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

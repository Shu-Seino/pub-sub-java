package com.mycompany.app;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PushConfig;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class CreateTopicExample {
    public static void main(String... args) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = CreateTopicExample.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            String projectId = prop.getProperty("project.id");
            String topicId = prop.getProperty("topic.id");
            String subscriptionId = prop.getProperty("subscription.id");

            try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
                ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
                topicAdminClient.createTopic(topicName);
                System.out.println("Topic created: " + topicName);
            }

            try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
                ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
                ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
                subscriptionAdminClient.createSubscription(subscriptionName, topicName, PushConfig.getDefaultInstance(), 0);
                System.out.println("Subscription created: " + subscriptionName);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
package com.mycompany.app;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SubscribeMessageExample {
    public static void main(String... args) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = SubscribeMessageExample.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            String projectId = prop.getProperty("project.id");
            String subscriptionId = prop.getProperty("subscription.id");

            ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
            MessageReceiver receiver =
                    (PubsubMessage message, AckReplyConsumer consumer) -> {
                        System.out.println("Received message: " + message.getData().toStringUtf8());
                        consumer.ack();
                    };
            Subscriber subscriber = null;
            try {
                subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
                subscriber.startAsync().awaitRunning();
                System.out.println("Listening for messages on " + subscriptionName + "...");
                Thread.sleep(10000);
            } finally {
                if (subscriber != null) {
                    subscriber.stopAsync();
                }
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
package com.mycompany.app;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PublishMessageExample {
    public static void main(String... args) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;

        try {
           input = PublishMessageExample.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            String projectId = prop.getProperty("project.id");
            String topicId = prop.getProperty("topic.id");

            TopicName topicName = TopicName.of(projectId, topicId);
            Publisher publisher = null;
            try {
                publisher = Publisher.newBuilder(topicName).build();

                String message = "Hello, world!";
                ByteString data = ByteString.copyFromUtf8(message);
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
                String messageId = messageIdFuture.get();
                System.out.println("Published message ID: " + messageId);
            } finally {
                if (publisher != null) {
                    publisher.shutdown();
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
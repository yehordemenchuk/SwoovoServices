package org.swoovo.support.util.kafka;

import org.apache.kafka.clients.admin.NewTopic;

public final class KafkaTopicUtil {
    public static NewTopic createNewTopic(String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    public static NewTopic createNewTopic(String topicName, int partitions, short replicationFactor) {
        return new NewTopic(topicName, partitions, replicationFactor);
    }
}

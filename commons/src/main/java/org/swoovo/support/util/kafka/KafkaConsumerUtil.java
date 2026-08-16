package org.swoovo.support.util.kafka;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Data
public final class KafkaConsumerUtil {
    private static final String TRUSTING_PACKAGES = "org.swoovo";

    private final String bootstrapServers;
    private final String trustingPackages;

    public <T> ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory(String groupId,
                                                                                                Class<T> dtoClass) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId, dtoClass));

        return factory;
    }

    public <T> ConsumerFactory<String, T> consumerFactory(String groupId, Class<T> dtoClass) {
        Map<String, Object> props = getConsumerConfigs();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, dtoClass);

        return new DefaultKafkaConsumerFactory<>(getConsumerConfigs());
    }

    public Map<String, Object> getConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTING_PACKAGES);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return props;
    }
}

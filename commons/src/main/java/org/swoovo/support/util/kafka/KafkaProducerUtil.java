package org.swoovo.support.util.kafka;

import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Data
public final class KafkaProducerUtil {
    private String bootstrapServers;

    public <T> KafkaTemplate<String, T> kafkaTemplate(
            ProducerFactory<String, T> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    public <T> DefaultKafkaProducerFactory<String, T> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerConfigs());
    }

    private Map<String, Object> getProducerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}

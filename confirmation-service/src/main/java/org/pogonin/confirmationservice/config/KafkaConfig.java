package org.pogonin.confirmationservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.pogonin.confirmationservice.dto.in.ConfirmCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    public String server;

    @Bean
    public Map<String, Object> consumerUserConfig() {
        Map<String, Object> consumer = new HashMap<>();
        consumer.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        consumer.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        consumer.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        consumer.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        consumer.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        consumer.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer.put(JsonDeserializer.KEY_DEFAULT_TYPE, "java.lang.String");
        consumer.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "org.pogonin.confirmationservice.dto.in.ConfirmCode");
        consumer.put(JsonDeserializer.TRUSTED_PACKAGES, "org.pogonin.confirmationservice");
        return consumer;
    }

    @Bean
    public ConsumerFactory<String, ConfirmCode> userConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerUserConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConfirmCode> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ConfirmCode> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        return factory;
    }
}

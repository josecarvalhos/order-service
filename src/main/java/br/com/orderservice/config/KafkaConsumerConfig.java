package br.com.orderservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.topic}")
    private String topicName;
    
    @Bean
    public ConcurrentMessageListenerContainer<String, String> messageListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties(topicName);
        containerProps.setMessageListener(messageListener());

        return new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);
    }
    
    @Bean
    public MessageListener<String, String> messageListener() {
        return record -> {
            logMessage(record);
            handleOrderMessage(record);
        };
    }
    
    private void logMessage(ConsumerRecord<String, String> record) {
        System.out.printf(
            "Received message: Key=%s, Value=%s, Partition=%d, Offset=%d%n",
            record.key(),
            record.value(),
            record.partition(),
            record.offset()
        );
    }
    
    private void handleOrderMessage(ConsumerRecord<String, String> record) {
        // TODO: Implementar lógica específica para processar a mensagem
        System.out.println("Processando mensagem: " + record.value());
    }


    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return configProps;
    }
}


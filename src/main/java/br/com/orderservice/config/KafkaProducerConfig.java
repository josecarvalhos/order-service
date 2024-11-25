package br.com.orderservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.producer.retries}")
	private int retries;

	@Value("${spring.kafka.producer.batch-size}")
	private int batchSize;

	@Value("${spring.kafka.producer.linger-ms}")
	private int lingerMs;

	@Value("${spring.kafka.producer.buffer-memory}")
	private long bufferMemory;

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	private Map<String, Object> producerConfigs() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
		configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		configProps.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
		configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		return configProps;
	}
}

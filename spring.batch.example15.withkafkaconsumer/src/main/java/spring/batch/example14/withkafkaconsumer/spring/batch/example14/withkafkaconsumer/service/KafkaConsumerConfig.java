package spring.batch.example14.withkafkaconsumer.spring.batch.example14.withkafkaconsumer.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.zaxxer.hikari.util.ClockSource.Factory;

import spring.batch.example14.withkafkaconsumer.spring.batch.example14.withkafkaconsumer.model.Employee;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	public KafkaConsumerConfig() {

	}

	@Bean
	public ConsumerFactory<String, Employee> consumerFactory() {
		JsonDeserializer<Employee> deserializer = new JsonDeserializer<>(Employee.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Employee> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<String, Employee>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		JsonDeserializer<Employee> deserializer = new JsonDeserializer<Employee>(Employee.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		Map<String, Object> propsMap = new HashMap<>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "boot");
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "123456");
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		propsMap.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "2000");

		return propsMap;
	}
}

package spring.batch.example14.withkafkaconsumer.spring.batch.example14.withkafkaconsumer.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import spring.batch.example14.withkafkaconsumer.spring.batch.example14.withkafkaconsumer.model.Employee;

@Component
public class Receiver {

	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(topics = "Employee")
	public void receiver(Employee employee) {
		System.out.println("Receive Employee details= '{}'" + employee.toString());
		latch.countDown();
	}
}

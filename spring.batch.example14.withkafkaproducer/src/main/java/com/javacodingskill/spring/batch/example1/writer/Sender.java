package com.javacodingskill.spring.batch.example1.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.javacodingskill.spring.batch.example1.model.Employee;

@Service
public class Sender {

	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplate;

	public void send(Employee employee) {
		kafkaTemplate.send("Employee", employee);
	}

}

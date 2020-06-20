package com.javacodingskill.spring.batch.example1.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.javacodingskill.spring.batch.example1.model.Employee;

public class EmployeeKafkaSender implements ItemWriter<Employee> {

	@Autowired
	private Sender sender;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		for (Employee employee : employees) {
			sender.send(employee);
		}

		System.out.println("Message sent to kafka");
	}

}

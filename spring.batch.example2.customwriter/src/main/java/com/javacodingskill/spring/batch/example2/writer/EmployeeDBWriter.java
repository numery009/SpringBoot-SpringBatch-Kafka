package com.javacodingskill.spring.batch.example2.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javacodingskill.spring.batch.example2.model.Employee;
import com.javacodingskill.spring.batch.example2.repo.EmployeeRepo;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDBWriter.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeRepo.saveAll(employees);
		logger.info("{} employees saved in database", employees.size());
	}
}

package com.javacodingskill.spring.batch.example5.multistepjob.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.javacodingskill.spring.batch.example5.multistepjob.model.Employee;
import com.javacodingskill.spring.batch.example5.multistepjob.repo.EmployeeRepo;

public class EmployeeDBWriter implements ItemWriter<Employee> {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		employeeRepo.saveAll(employees);
		System.out.println("Inside writer " + employees);
	}

}

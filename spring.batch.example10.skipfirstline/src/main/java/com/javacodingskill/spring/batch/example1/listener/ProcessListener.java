package com.javacodingskill.spring.batch.example1.listener;

import org.springframework.batch.core.ItemProcessListener;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example1.model.Employee;

public class ProcessListener implements ItemProcessListener<EmployeeDTO, Employee> {

	@Override
	public void beforeProcess(EmployeeDTO employeeDTO) {
		System.out.println("Before Process: " + employeeDTO.toString());
	}

	@Override
	public void afterProcess(EmployeeDTO employeeDTO, Employee employee) {
		System.out.println("After Process: "+ employee.toString());
	}

	@Override
	public void onProcessError(EmployeeDTO item, Exception e) {
		System.out.println("On error: " + e.getMessage());
	}

}

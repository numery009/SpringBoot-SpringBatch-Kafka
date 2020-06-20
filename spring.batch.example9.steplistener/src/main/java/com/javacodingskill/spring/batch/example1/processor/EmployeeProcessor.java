package com.javacodingskill.spring.batch.example1.processor;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example1.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

	@Autowired
	private ExecutionContext executionContext;

	@Override
	public Employee process(EmployeeDTO employeeDTO) throws Exception {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeDTO.getEmployeeId() + executionContext.getString("customFileName"));
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setAge(employeeDTO.getAge());
		if (employee.getAge()==56) {
			throw new ArithmeticException("56 not allowed");
		}
		return employee;
	}

}

package com.javacodingskill.spring.batch.example2.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.javacodingskill.spring.batch.example2.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example2.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

	@Override
	public Employee process(EmployeeDTO employeeDTO) throws Exception {
		Employee employee = new Employee();
		if (!isValid(employeeDTO)) {
			return null;
		}
		employee.setEmployeeId(employeeDTO.getEmployeeId());
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setAge(employeeDTO.getAge());
		return employee;
	}

	boolean isValid(EmployeeDTO employeeDTO) {
		return employeeDTO.getFirstName().startsWith("AAA") ? false : true;
	}

}

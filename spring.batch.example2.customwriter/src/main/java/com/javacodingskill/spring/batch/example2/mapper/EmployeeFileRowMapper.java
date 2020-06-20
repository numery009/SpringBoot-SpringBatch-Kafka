package com.javacodingskill.spring.batch.example2.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import com.javacodingskill.spring.batch.example2.dto.EmployeeDTO;

@Component
public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDTO> {

	public EmployeeDTO mapFieldSet(FieldSet fieldSet) {
		EmployeeDTO employee = new EmployeeDTO();
		employee.setEmployeeId(fieldSet.readString("employeeId"));
		employee.setFirstName(fieldSet.readString("firstName"));
		employee.setLastName(fieldSet.readString("lastName"));
		employee.setEmail(fieldSet.readString("email"));
		try {
			employee.setAge(fieldSet.readInt("age"));
		} catch (Exception e) {

		}
		return employee;
	}
}

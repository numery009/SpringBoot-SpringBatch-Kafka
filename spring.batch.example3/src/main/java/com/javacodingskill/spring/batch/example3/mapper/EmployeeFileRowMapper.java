package com.javacodingskill.spring.batch.example3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.javacodingskill.spring.batch.example3.model.Employee;

@Component
public class EmployeeFileRowMapper implements RowMapper<Employee> {

	public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(resultSet.getString("employee_id"));
		employee.setFirstName(resultSet.getString("first_name"));
		employee.setLastName(resultSet.getString("last_name"));
		employee.setEmail(resultSet.getString("email"));
		employee.setAge(resultSet.getInt("age"));
		return employee;
	}
}

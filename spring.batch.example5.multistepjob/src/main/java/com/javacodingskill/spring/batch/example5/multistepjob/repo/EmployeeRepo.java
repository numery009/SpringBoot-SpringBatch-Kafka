package com.javacodingskill.spring.batch.example5.multistepjob.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javacodingskill.spring.batch.example5.multistepjob.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

}

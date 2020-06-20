package spring.batch.example14.withkafkaconsumer.spring.batch.example14.withkafkaconsumer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Employee {
	@Id
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private int age;
}

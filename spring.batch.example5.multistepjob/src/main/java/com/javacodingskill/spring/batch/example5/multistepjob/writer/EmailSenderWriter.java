package com.javacodingskill.spring.batch.example5.multistepjob.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.javacodingskill.spring.batch.example5.multistepjob.dto.EmployeeDTO;

public class EmailSenderWriter implements ItemWriter<EmployeeDTO>{

	@Override
	public void write(List<? extends EmployeeDTO> items) throws Exception {		
		System.out.println("Email send successfully to all the employees.");
	}

}

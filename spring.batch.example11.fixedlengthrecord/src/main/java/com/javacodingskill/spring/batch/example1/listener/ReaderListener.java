package com.javacodingskill.spring.batch.example1.listener;

import org.springframework.batch.core.ItemReadListener;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;

public class ReaderListener implements ItemReadListener<EmployeeDTO> {

	@Override
	public void beforeRead() {
		System.out.println("Before Read Operation.");
	}

	@Override
	public void afterRead(EmployeeDTO item) {
		System.out.println("After Reading :" + item.toString());
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("On error while reading :" + ex.toString());
	}

}

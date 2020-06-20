package com.javacodingskill.spring.batch.example1.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.javacodingskill.spring.batch.example1.model.Employee;

public class WriterListener implements ItemWriteListener<Employee> {

	@Override
	public void beforeWrite(List<? extends Employee> items) {
		System.out.println("Before write :");
		items.stream().forEach(System.out::println);
	}

	@Override
	public void afterWrite(List<? extends Employee> items) {
		System.out.println("after write");
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Employee> items) {
		System.out.println("on write error :"+ exception.toString());
	}

}

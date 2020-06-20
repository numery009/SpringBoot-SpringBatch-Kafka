package com.javacodingskill.spring.batch.example1.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.javacodingskill.spring.batch.example1.repo.EmployeeRepo;

public class DataCleanUp implements Tasklet {

	private EmployeeRepo employeeRepo;

	public DataCleanUp(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		employeeRepo.deleteAll();
		return RepeatStatus.FINISHED;
	}

}

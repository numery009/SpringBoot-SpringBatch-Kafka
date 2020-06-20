package com.javacodingskill.spring.batch.example1.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class Demo7Listener extends JobExecutionListenerSupport {

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("After demo7 execution. The value of beforeJob key is="
				+ jobExecution.getJobInstance().getJobName() + " execution");
		if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
			System.out.println("Success");
		} else {
			System.out.println("Failed");
		}
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Before " + jobExecution.getJobInstance().getJobName() + " execution");
		jobExecution.getExecutionContext().putString("beforeJob", "beforeValue");
	}
}

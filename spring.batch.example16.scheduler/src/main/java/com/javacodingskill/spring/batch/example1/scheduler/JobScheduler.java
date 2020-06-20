package com.javacodingskill.spring.batch.example1.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.javacodingskill.spring.batch.example1.runner.JobRunner;

@Configuration
public class JobScheduler {

	private JobRunner jobRunner;

	public JobScheduler(JobRunner jobRunner) {
		this.jobRunner = jobRunner;
	}

	@Scheduled(cron = "0 0/2 * 1/1 * ?")
	public void jobScheduled() {
		System.out.println("Job triggered");
		jobRunner.runBatchJob();
	}
}

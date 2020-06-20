package com.javacodingskill.spring.batch.example1.job;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class JobSkipPolicy implements SkipPolicy {

	@Override
	public boolean shouldSkip(Throwable t, int failedCount) throws SkipLimitExceededException {

		return failedCount > 2 ? false : true;
	}

}

package com.javacodingskill.spring.batch.example1.handler;

import org.springframework.batch.item.file.LineCallbackHandler;

public class SkipRecordCallback implements LineCallbackHandler {

	@Override
	public void handleLine(String s) {
		System.out.println("##### First record data ####" +s);
	}

}

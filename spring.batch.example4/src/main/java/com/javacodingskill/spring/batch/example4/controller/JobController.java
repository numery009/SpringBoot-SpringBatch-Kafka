package com.javacodingskill.spring.batch.example4.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacodingskill.spring.batch.example4.runner.JobRunner;

/*
url: http://localhost:8080/run/job
 */

@RestController
@RequestMapping("/run")
public class JobController {

    private JobRunner jobRunner;

    @Autowired
    public JobController(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }

    @RequestMapping(value = "/job")
    public String runJob() {
    	LocalTime now1=LocalTime.now()    ;			
    	    	
    	System.out.println("Start Time - " + now1);
        jobRunner.runBatchJob();
        System.out.println("End Time - " + now1);
        return String.format("Job Demo1 submitted successfully.");
    }
}
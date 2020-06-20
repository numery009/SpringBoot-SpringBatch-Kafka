package com.javacodingskill.spring.batch.example1.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example1.mapper.EmployeeFileRowMapper;
import com.javacodingskill.spring.batch.example1.model.Employee;
import com.javacodingskill.spring.batch.example1.processor.EmployeeProcessor;
import com.javacodingskill.spring.batch.example1.repo.EmployeeRepo;
import com.javacodingskill.spring.batch.example1.tasklet.AgeGroupSummary;
import com.javacodingskill.spring.batch.example1.tasklet.DataCleanUp;

@Configuration
public class Demo1 {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	EmployeeRepo employeeRepo;

	// @Qualifier(value = "demo1")
	@Bean
	public Job demo1Job() throws Exception {
		return this.jobBuilderFactory.get("demo1").start(step1Demo()).build();
	}

	public Step step1Demo() throws Exception {
		return this.stepBuilderFactory.get("step1").tasklet(new AgeGroupSummary()).build();

	}

}

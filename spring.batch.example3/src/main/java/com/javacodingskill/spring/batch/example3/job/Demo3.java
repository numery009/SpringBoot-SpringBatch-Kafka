package com.javacodingskill.spring.batch.example3.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.javacodingskill.spring.batch.example3.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example3.mapper.EmployeeFileRowMapper;
import com.javacodingskill.spring.batch.example3.model.Employee;
import com.javacodingskill.spring.batch.example3.processor.EmployeeProcessor;

@Configuration
public class Demo3 {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private EmployeeProcessor employeeProcessor;
	@Autowired
	private EmployeeFileRowMapper employeeFileRowMapper;
	@Autowired
	private DataSource dataSource;

	private Resource outputResource = new FileSystemResource("output/employee_outout.csv");

	// @Qualifier(value = "demo1")
	@Bean
	public Job demo3Job() throws Exception {
		return this.jobBuilderFactory.get("demo3").start(step1Demo()).build();
	}

	public Step step1Demo() throws Exception {
		return this.stepBuilderFactory.get("step1").<Employee, EmployeeDTO>chunk(10).reader(employeeDBReader())
				.processor(employeeProcessor)
				.writer(employeeWriter()).build();

	}

	@Bean
	public ItemStreamReader<Employee> employeeDBReader(){
		JdbcCursorItemReader<Employee> reader=new JdbcCursorItemReader<Employee>();
		reader.setDataSource(dataSource);
		reader.setSql("select * from employee");
		reader.setRowMapper(employeeFileRowMapper);
		return reader;
	}

	@Bean
	public ItemWriter<EmployeeDTO> employeeWriter() throws Exception {
		FlatFileItemWriter<EmployeeDTO> write = new FlatFileItemWriter<EmployeeDTO>();
		write.setResource(outputResource);
		write.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
			{
				setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
					{
						setNames(new String[] { "employeeId", "firstName", "lastName", "email", "age" });
					}

				});
			}
		});
		
		write.setShouldDeleteIfExists(true);
		return write;
	}

}

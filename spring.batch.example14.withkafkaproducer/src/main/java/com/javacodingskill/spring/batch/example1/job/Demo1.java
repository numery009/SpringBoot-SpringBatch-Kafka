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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example1.mapper.EmployeeFileRowMapper;
import com.javacodingskill.spring.batch.example1.model.Employee;
import com.javacodingskill.spring.batch.example1.processor.EmployeeProcessor;
import com.javacodingskill.spring.batch.example1.writer.EmployeeKafkaSender;

@Configuration
public class Demo1 {

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

	// @Qualifier(value = "demo1")
	@Bean
	public Job demo1Job() throws Exception {
		return this.jobBuilderFactory.get("demo1").start(step1Demo()).build();
	}

	public Step step1Demo() throws Exception {
		return this.stepBuilderFactory.get("step1").<EmployeeDTO, Employee>chunk(5).reader(employeeReader())
				.processor(employeeProcessor).writer(employeeKafkaSender()).build();

	}

	@Bean
	@StepScope
	Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
		return new ClassPathResource(fileName);
	}

	@Bean
	@StepScope
	public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
		FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
		reader.setResource(inputFileResource(null));
		reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "firstName", "lastName", "email", "age");
						setDelimiter(",");
					}
				});
				setFieldSetMapper(employeeFileRowMapper);
			}
		});

		return reader;
	}

	@Bean
	public EmployeeKafkaSender employeeKafkaSender() {
		return new EmployeeKafkaSender();
	}

}

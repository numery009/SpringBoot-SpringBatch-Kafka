package com.javacodingskill.spring.batch.example5.multistepjob.job;

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

import com.javacodingskill.spring.batch.example5.multistepjob.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example5.multistepjob.mapper.EmployeeDBRowMapper;
import com.javacodingskill.spring.batch.example5.multistepjob.mapper.EmployeeFileRowMapper;
import com.javacodingskill.spring.batch.example5.multistepjob.model.Employee;
import com.javacodingskill.spring.batch.example5.multistepjob.processor.EmployeeProcessor;
import com.javacodingskill.spring.batch.example5.multistepjob.writer.EmailSenderWriter;

@Configuration
public class Demo5 {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private EmployeeProcessor employeeProcessor;
	@Autowired
	private EmployeeFileRowMapper employeeFileRowMapper;
	@Autowired
	private EmployeeDBRowMapper employeeDBRowMapper;
	@Autowired
	private DataSource dataSource;

	private Resource outputResource = new FileSystemResource("output/employee_outout.csv");

	@Qualifier(value = "demo5")
	@Bean
	public Job demo5Job() throws Exception {
		return this.jobBuilderFactory.get("demo5").start(step1Demo5())
				.next(step2Demo5())
				.build();
	}

	public Step step1Demo5() throws Exception {
		return this.stepBuilderFactory.get("step1").<EmployeeDTO, Employee>chunk(10).reader(employeeReader())
				.processor(employeeProcessor).writer(employeeDOWriterDefault()).build();

	}

	public Step step2Demo5() throws Exception {
		return this.stepBuilderFactory.get("step2").<Employee, EmployeeDTO>chunk(10).reader(employeeDBReader())
				.writer(employeeWriter()).build();

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
	public JdbcBatchItemWriter<Employee> employeeDOWriterDefault() {
		JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();

		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return itemWriter;

	}

	@Bean
	public ItemStreamReader<Employee> employeeDBReader() {
		JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<Employee>();
		reader.setDataSource(dataSource);
		reader.setSql("select * from employee");
		reader.setRowMapper(employeeDBRowMapper);
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

	@Bean
	EmailSenderWriter emailSenderWriter() {
		return new EmailSenderWriter();
	}

}

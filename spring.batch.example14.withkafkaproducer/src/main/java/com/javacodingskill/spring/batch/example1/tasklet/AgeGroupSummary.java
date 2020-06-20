package com.javacodingskill.spring.batch.example1.tasklet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.CollectionUtils;

import com.javacodingskill.spring.batch.example1.dto.EmployeeDTO;
import com.javacodingskill.spring.batch.example1.utils.Constants;

public class AgeGroupSummary implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		try (Stream<String> employees = Files.lines(Paths.get(Constants.FILE_PATH_EMPCSV))) {
			List<EmployeeDTO> employeeDTOList = employees.map((strData -> strData.split(",")))
					.map(AgeGroupSummary::employeeMapper).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(employeeDTOList)) {
				Map<Integer, Long> ageGroupMap = employeeDTOList.stream()
						.collect(Collectors.groupingBy(EmployeeDTO::getAge, Collectors.counting()));

				System.out.println(ageGroupMap);
				
				
				 Map<String, Integer> ageGroupMap1 = employeeDTOList.stream()
				 .collect(Collectors.toMap(EmployeeDTO::getEmployeeId, EmployeeDTO::getAge));
				 
				 System.out.println(ageGroupMap1);
				 
				
			}
		}

		return RepeatStatus.FINISHED;
	}

	private static EmployeeDTO employeeMapper(String[] record) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeId(record[0]);
		employeeDTO.setFirstName(record[1]);
		employeeDTO.setLastName(record[2]);
		employeeDTO.setEmail(record[3]);
		employeeDTO.setAge(Integer.parseInt(record[4]));
		return employeeDTO;
	}

}

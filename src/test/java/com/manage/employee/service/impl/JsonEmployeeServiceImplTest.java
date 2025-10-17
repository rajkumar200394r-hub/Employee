package com.manage.employee.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.manage.employee.dto.EmployeeData;
import com.manage.employee.utils.AppContext;

@SpringBootTest
public class JsonEmployeeServiceImplTest {

	private static final Log LOG = LogFactory.getLog(JsonEmployeeServiceImplTest.class);

	@Test
	public void testGetAllEmployeeData() {

		JsonEmployeeServiceImpl employeeService = AppContext.getBean(JsonEmployeeServiceImpl.class);
		List<EmployeeData> employeeDataFromConcurrent = employeeService.getAll();
		int empDataConcurrentSize = employeeDataFromConcurrent.size();
		LOG.info(" employee Data Concurrent HashMap Size " + empDataConcurrentSize);
		assertEquals(1000, empDataConcurrentSize, "Failed concurrent HashMap");

	}

	@Test
	public void testGetAllFromHashMap() {

		JsonEmployeeServiceImpl employeeService = AppContext.getBean(JsonEmployeeServiceImpl.class);
		List<EmployeeData> empDataHashMap = employeeService.getAllfromHashMap();
		int empDataHashMapSize = empDataHashMap.size();
		LOG.info(" employee Data HashMap Size " + empDataHashMapSize);
		assertEquals(1000, empDataHashMapSize, "Failed HashMap");

	}

}

package com.manage.employee.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.employee.dto.EmployeeData;
import com.manage.employee.dto.EmployeeFilter;
import com.manage.employee.dto.EmployeeRequest;
import com.manage.employee.dto.EmployeeResponse;
import com.manage.employee.exception.EmployeeException;
import com.manage.employee.service.EmployeeService;

import jakarta.annotation.PostConstruct;

@Service
public class JsonEmployeeServiceImpl implements EmployeeService {

	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<Long, EmployeeData> store = new ConcurrentHashMap<>();
	private final Map<Long, EmployeeData> hashMapStore = new HashMap<>();
	private final File file;

	public JsonEmployeeServiceImpl(@Value("${app.json.file}") String path) {
		this.file = new File(path);
	}

	@PostConstruct
	private void init() throws InterruptedException {
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				mapper.writeValue(file, Collections.emptyList());
			}
			List<EmployeeData> list = mapper.readValue(file, new TypeReference<List<EmployeeData>>() {
			});
			ExecutorService executor = Executors.newFixedThreadPool(100);
			executor.submit(()->{
			for (EmployeeData e : list) {
				store.put(e.getEmpId(), e);
				hashMapStore.put(e.getEmpId(), e);
			}});
			
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (IOException e) {
			throw new RuntimeException("Failed to initialize JSON store: " + e.getMessage(), e);
		}
	}

	private synchronized void persist() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>(store.values()));
		} catch (IOException e) {
			throw new RuntimeException("Failed to persist JSON store: " + e.getMessage(), e);
		}
	}

	@Override
	public EmployeeResponse create(EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeData empData = request.getData();

		if (store.containsKey(empData.getEmpId())) {
			throw new EmployeeException("Employee already exists: " + empData.getEmpId(), "Failed");
		}

		store.put(empData.getEmpId(), empData);
		persist();
		employeeResponse.setMessage("Employee created successfully: "+ empData.getEmpId());
		employeeResponse.setStatus("Success");
		employeeResponse.setData(empData);
		return employeeResponse;
	}

	@Override
	public EmployeeResponse update(EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeData empData = request.getData();

		if (store.containsKey(empData.getEmpId())) {
			EmployeeData employeeData = store.get(empData.getEmpId());
			employeeData.setFirstName(empData.getFirstName());
			employeeData.setLastName(empData.getLastName());
			employeeData.setEmail(empData.getEmail());
			employeeData.setMobile(empData.getMobile());
			employeeData.setDepartment(empData.getDepartment());
			employeeData.setSalary(empData.getSalary());
			store.put(employeeData.getEmpId(), employeeData);
			persist();
			employeeResponse.setMessage("Employee data updated successfully: " + empData.getEmpId());
			employeeResponse.setStatus("Success");
			employeeResponse.setData(employeeData);
		} else {
			throw new EmployeeException("Employee not found: " + empData.getEmpId(), "Failed");
		}

		return employeeResponse;

	}

	@Override
	public EmployeeResponse getById(EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeFilter empFilter = request.getFilter();

		if (store.containsKey(empFilter.getEmpId())) {
			EmployeeData employeeData = store.get(empFilter.getEmpId());
			store.put(employeeData.getEmpId(), employeeData);
			persist();
			employeeResponse.setStatus("Success");
			employeeResponse.setData(employeeData);
		} else {
			throw new EmployeeException("Employee not found: " + empFilter.getEmpId(), "Failed");
		}

		return employeeResponse;

	}

	@Override
	public List<EmployeeData> getAll() {

		return new ArrayList<>(store.values());

	}
	
	public List<EmployeeData> getAllfromHashMap() {

		return new ArrayList<>(hashMapStore.values());

	}

	@Override
	public EmployeeResponse delete(EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeFilter empFilter = request.getFilter();

		if (store.containsKey(empFilter.getEmpId())) {
			store.remove(empFilter.getEmpId());
			persist();
			employeeResponse.setStatus("Success");
			employeeResponse.setMessage("Employee Data delete successfully: " + empFilter.getEmpId());
		} else {
			throw new EmployeeException("Employee not found: " + empFilter.getEmpId(), "Failed");
		}

		return employeeResponse;
	}

}

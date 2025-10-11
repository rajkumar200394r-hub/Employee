package com.manage.employee.service;

import java.util.List;

import com.manage.employee.dto.EmployeeData;
import com.manage.employee.dto.EmployeeRequest;
import com.manage.employee.dto.EmployeeResponse;

public interface EmployeeService {
	EmployeeResponse create(EmployeeRequest request);

	EmployeeResponse update(EmployeeRequest request);

	EmployeeResponse getById(EmployeeRequest request);

	List<EmployeeData> getAll();

	EmployeeResponse delete(EmployeeRequest request);
}

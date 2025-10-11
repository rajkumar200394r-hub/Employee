package com.manage.employee.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

	String source;

	EmployeeData data;

	EmployeeFilter filter;

}

package com.manage.employee.utils;

import com.manage.employee.dto.EmployeeData;
import com.manage.employee.entity.EmployeeEntity;

public class EmployeeMapper {

	public static EmployeeEntity toEntity(EmployeeData empData) {
		EmployeeEntity empEntity = new EmployeeEntity();
		empEntity.setEmpId(empData.getEmpId());
		empEntity.setFirstName(empData.getFirstName());
		empEntity.setLastName(empData.getLastName());
		empEntity.setEmail(empData.getEmail());
		empEntity.setMobile(empData.getMobile());
		empEntity.setDepartment(empData.getDepartment());
		empEntity.setSalary(empData.getSalary());
		return empEntity;
	}
	
	public static EmployeeData toData(EmployeeEntity empEntity) {
		EmployeeData empData = new EmployeeData();
		empData.setEmpId(empEntity.getEmpId());
		empData.setFirstName(empEntity.getFirstName());
		empData.setLastName(empEntity.getLastName());
		empData.setEmail(empEntity.getEmail());
		empData.setMobile(empEntity.getMobile());
		empData.setDepartment(empEntity.getDepartment());
		empData.setSalary(empEntity.getSalary());
		return empData;
	}

}

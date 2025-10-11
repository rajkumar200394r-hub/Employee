package com.manage.employee.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manage.employee.dto.EmployeeData;
import com.manage.employee.dto.EmployeeFilter;
import com.manage.employee.dto.EmployeeRequest;
import com.manage.employee.dto.EmployeeResponse;
import com.manage.employee.entity.EmployeeEntity;
import com.manage.employee.exception.EmployeeException;
import com.manage.employee.repository.EmployeeRepository;
import com.manage.employee.service.EmployeeService;
import com.manage.employee.utils.EmployeeMapper;

@Service
public class H2EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repository;

	public H2EmployeeServiceImpl(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public EmployeeResponse create(EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();

		EmployeeData empData = request.getData();
		long empId = empData.getEmpId();
		Optional<EmployeeEntity> existing = repository.findByEmpId(empId);
		if (existing.isPresent()) {
			throw new EmployeeException("Employee already exists: " + empData.getEmpId(),"Failed");
		}
		EmployeeEntity empEntity = EmployeeMapper.toEntity(empData);
		repository.save(empEntity);
		employeeResponse.setMessage("Employee created successfully: "+ empData.getEmpId());
		employeeResponse.setStatus("Success");
		employeeResponse.setData(empData);
		return employeeResponse;
	}

	@Override
	public EmployeeResponse update(EmployeeRequest request) {
		
		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeData empData = request.getData();
		
		long empId = empData.getEmpId();
		Optional<EmployeeEntity> existing = repository.findByEmpId(empId);
		
		if (existing.isPresent()) {
			EmployeeEntity employeeEntity = existing.get();
			long existingEmpId = employeeEntity.getId();
			employeeEntity = EmployeeMapper.toEntity(empData);
			employeeEntity.setId(existingEmpId);
			repository.save(employeeEntity);
			employeeResponse.setMessage("Employee data updated successfully: " + empId);
			employeeResponse.setStatus("Success");
			employeeResponse.setData(empData);
		} else {
			throw new EmployeeException("Employee not Found : " + empId, "Failed");
		}

		return employeeResponse;
	}

	@Override
	public EmployeeResponse getById(EmployeeRequest request) {
		
		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeFilter empFilter = request.getFilter();

		long empId = empFilter.getEmpId();
		Optional<EmployeeEntity> existing = repository.findByEmpId(empId);
		if (existing.isPresent()) {
			EmployeeEntity employeeEntity = existing.get();
			employeeResponse.setStatus("Success");
			employeeResponse.setData(EmployeeMapper.toData(employeeEntity));
		} else {
			throw new EmployeeException("Employee not Found: " + empId, "Failed");
		}

		return employeeResponse;
	}

	@Override
	public List<EmployeeData> getAll() {
		List<EmployeeEntity> employeeEntityList = repository.findAll();
		return employeeEntityList.stream().map(EmployeeMapper::toData).toList();
	}

	@Override
	public EmployeeResponse delete(EmployeeRequest request) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeFilter empFilter = request.getFilter();

		long empId = empFilter.getEmpId();
		Optional<EmployeeEntity> existing = repository.findByEmpId(empId);
		if (existing.isPresent()) {
			repository.deleteById(existing.get().getId());
			employeeResponse.setStatus("Success");
			employeeResponse.setMessage("Employee Data delete successfully: " + empId);
		} else {
			throw new EmployeeException("Employee not Found : " + empId, "Failed");
		}

		return employeeResponse;
	}

}

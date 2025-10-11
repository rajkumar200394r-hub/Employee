package com.manage.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manage.employee.dto.EmployeeData;
import com.manage.employee.dto.EmployeeRequest;
import com.manage.employee.dto.EmployeeResponse;
import com.manage.employee.exception.EmployeeException;
import com.manage.employee.service.EmployeeService;
import com.manage.employee.service.impl.H2EmployeeServiceImpl;
import com.manage.employee.service.impl.JsonEmployeeServiceImpl;

@RestController
@RequestMapping("/manage/employee")
public class EmployeeController {
	
	private static final Log LOG = LogFactory.getLog(EmployeeController.class);
	
	private final H2EmployeeServiceImpl h2Service;
    private final JsonEmployeeServiceImpl jsonService;

    public EmployeeController(H2EmployeeServiceImpl h2Service, JsonEmployeeServiceImpl jsonService) {
        this.h2Service = h2Service;
        this.jsonService = jsonService;
    }

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {

			String source = request.getSource();
			boolean checkValidSource = checkValidSource(source);

			if (!checkValidSource) {
				employeeResponse.setStatus("Error");
				employeeResponse.setMessage("kindly enter valid source");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			EmployeeService empService = selectService(source);
			employeeResponse = empService.create(request);
		} catch (EmployeeException e) {
			employeeResponse.setStatus(e.getStatus());
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		} catch (Exception e) {
			employeeResponse.setStatus("Failed");
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {

			String source = request.getSource();
			boolean checkValidSource = checkValidSource(source);

			if (!checkValidSource) {
				employeeResponse.setStatus("Error");
				employeeResponse.setMessage("kindly enter valid source");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			EmployeeService empService = selectService(source);
			employeeResponse = empService.update(request);
		} catch (EmployeeException e) {
			employeeResponse.setStatus(e.getStatus());
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		} catch (Exception e) {
			employeeResponse.setStatus("Failed");
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EmployeeResponse> fetchEmployee(@RequestBody EmployeeRequest request) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {

			String source = request.getSource();
			boolean checkValidSource = checkValidSource(source);

			if (!checkValidSource) {
				employeeResponse.setStatus("Error");
				employeeResponse.setMessage("kindly enter valid source");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			EmployeeService empService = selectService(source);
			employeeResponse = empService.getById(request);
		} catch (EmployeeException e) {
			employeeResponse.setStatus(e.getStatus());
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		} catch (Exception e) {
			employeeResponse.setStatus("Failed");
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}
	
	@PostMapping(value = "/fetchall", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> fetchAllEmployee(@RequestBody EmployeeRequest request) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		List<EmployeeData> employeeDataList = new ArrayList<>();
		try {

			String source = request.getSource();
			boolean checkValidSource = checkValidSource(source);

			if (!checkValidSource) {
				employeeResponse.setStatus("Error");
				employeeResponse.setMessage("kindly enter valid source");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			EmployeeService empService = selectService(source);
			employeeDataList = empService.getAll();
			
			if (employeeDataList.isEmpty()) {
				employeeResponse.setStatus("Success");
				employeeResponse.setMessage("No Data Found");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
		} catch (EmployeeException e) {
			employeeResponse.setStatus(e.getStatus());
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		} catch (Exception e) {
			employeeResponse.setStatus("Failed");
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(employeeDataList, HttpStatus.OK);
	}

	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EmployeeResponse> deleteEmployee(@RequestBody EmployeeRequest request) {

		EmployeeResponse employeeResponse = new EmployeeResponse();
		try {

			String source = request.getSource();
			boolean checkValidSource = checkValidSource(source);

			if (!checkValidSource) {
				employeeResponse.setStatus("Error");
				employeeResponse.setMessage("kindly enter valid source");
				return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
			}
			EmployeeService empService = selectService(source);
			employeeResponse = empService.delete(request);
		} catch (EmployeeException e) {
			employeeResponse.setStatus(e.getStatus());
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		} catch (Exception e) {
			employeeResponse.setStatus("Failed");
			employeeResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(employeeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
	}
	
	private EmployeeService selectService(String source) {
		if ("H2".equalsIgnoreCase(source)) {
			return h2Service;
		} else {
			return jsonService;
		}
	}
    
	@SuppressWarnings("unused")
	private boolean checkValidSource(String source) {
		if (source.isEmpty() && source == null) {
			return false;
		} else if ("h2".equalsIgnoreCase(source) || "JSON".equalsIgnoreCase(source)) {
			return true;
		} else {
			return false;
		}

	}

}

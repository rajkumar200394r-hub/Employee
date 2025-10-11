package com.manage.employee.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployeeData {
	
    @JsonProperty("empId")
    private long empId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("mobile")
    private long mobile;
    
    @JsonProperty("department")
    private String department;
    
    @JsonProperty("salary")
    private BigDecimal salary;

}
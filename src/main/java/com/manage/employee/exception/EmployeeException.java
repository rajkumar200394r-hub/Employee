package com.manage.employee.exception;

public class EmployeeException extends RuntimeException {
	private final String status;

	public EmployeeException(String message, String status) {
		super(message);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}

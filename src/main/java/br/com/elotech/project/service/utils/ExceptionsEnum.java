package br.com.elotech.project.service.utils;

import java.util.stream.Stream;

public enum ExceptionsEnum {
	IllegalArgument("IllegalArgumentException"), 
	NotFound("NotFoundException"), 
	DataIntegrityViolation("DataIntegrityViolationException"),
	Business("BusinessException"),
	Messaging("MessagingException"),
	ServiceDown("ServiceDownException"),
	SQLIntegrityConstraintViolation("SQLIntegrityConstraintViolationException"),
	PSQLException("PSQLException"),
	ExpiredJwtException("ExpiredJwtException"),
	Exception("Exception");
 
	private String name;
 
	private ExceptionsEnum(String name) {
		this.name = name;
    }
 
	public String getName() {
		return name;
    }
 
	public static ExceptionsEnum of(String name) throws IllegalArgumentException {
		return Stream.of(ExceptionsEnum.values())
				.filter(p -> p.getName().equalsIgnoreCase(name))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}

package br.com.elotech.project.service.utils;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private String field;
	private Object value;

	public BusinessException(String message) {
		super();
		this.message = message;
	}

	public BusinessException(String message, String field, Object value) {
		super();
		this.message = message;
		this.field = field;
		this.value = value;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}

}

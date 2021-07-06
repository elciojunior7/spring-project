package br.com.elotech.project.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FactoryException {

	public FactoryException() {
		// TODO Auto-generated constructor stub
	}
	
	public static ResponseEntity<Object> build(Exception e, String name, String msg, String defaultMsg) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("msg", msg != null ? msg : defaultMsg);
		if (name.equals(ExceptionsEnum.IllegalArgument.getName()))
			return ResponseEntity.badRequest().body(response);
		else if (name.equals(ExceptionsEnum.NotFound.getName()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		else if (name.equals(ExceptionsEnum.ServiceDown.getName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		} else if (name.equals(ExceptionsEnum.DataIntegrityViolation.getName())) {
			String[] arr = msg.split(":");
			Throwable cause = e.getCause();
			if (arr[0].trim().equalsIgnoreCase("not-null property references a null or transient value")) {
				PropertyValueException prop = (PropertyValueException) cause;
				response.put("msg", "Preencha todos os campos obrigatórios");
				response.put("field", prop.getPropertyName());
				return ResponseEntity.badRequest().body(response);
			} else if (cause.getCause().toString().contains(ExceptionsEnum.SQLIntegrityConstraintViolation.getName())
					|| cause.getCause().toString().contains("PSQLException")) {
				if (msg.contains("uk_email")) {
					response.put("msg", "E-mail já existe.");
				} else if (msg.contains("uk_cpf")) {
					response.put("msg", "CPF já existe.");
				}
			}
			return ResponseEntity.badRequest().body(response);
		} else if (name.equals(ExceptionsEnum.Business.getName())) {
			BusinessException be = (BusinessException) e;
			if (be.getField() != null)
				response.put("field", be.getField());
			if (be.getValue() != null)
				response.put("value", be.getValue());
			return ResponseEntity.badRequest().body(response);
		} else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

	}

}

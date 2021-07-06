package br.com.elotech.project.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.elotech.project.dto.PersonDTO;
import br.com.elotech.project.service.PersonService;
import br.com.elotech.project.service.utils.FactoryException;
import br.com.elotech.project.service.utils.Pageable;
import br.com.elotech.project.service.utils.PersonErrorEnum;

@CrossOrigin
@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping("")
	public ResponseEntity<Object> save(@Valid @RequestBody PersonDTO dto) {
		try {
			dto = personService.add(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					PersonErrorEnum.CREATE_ERROR.getName());
		}
	}

	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody PersonDTO dto) {
		try {
			dto = personService.update(dto);
			return ResponseEntity.ok(dto);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					PersonErrorEnum.UPDATE_ERROR.getName());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		try {
			PersonDTO dto = new PersonDTO();
			dto.setId(id);
			personService.delete(dto);
			return ResponseEntity.noContent().build();

		} catch (DataIntegrityViolationException e) {
			return FactoryException.build(e, e.getClass().getSimpleName(),
					PersonErrorEnum.DELETE_ATTACHED_ERROR.getName(),
					PersonErrorEnum.DELETE_ERROR.getName());
		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					PersonErrorEnum.DELETE_ERROR.getName());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id) {
		try {
			PersonDTO dto = personService.findById(id);
			return ResponseEntity.ok(dto);
		
		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					PersonErrorEnum.GET_ERROR.getName());
		}
	}

	@GetMapping("")
	public ResponseEntity<Object> getCepByAddress(
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf) {
		try {
			Pageable list = personService.findAll(offset, limit, name, cpf);
			return ResponseEntity.ok(list);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					PersonErrorEnum.LIST_ERROR.getName());
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, fieldName + " " + errorMessage);
		});
		return errors;
	}

}

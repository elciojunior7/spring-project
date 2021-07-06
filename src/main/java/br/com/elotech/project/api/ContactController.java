package br.com.elotech.project.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.elotech.project.dto.ContactDTO;
import br.com.elotech.project.dto.PersonDTO;
import br.com.elotech.project.service.ContactService;
import br.com.elotech.project.service.utils.ContactErrorEnum;
import br.com.elotech.project.service.utils.FactoryException;
import br.com.elotech.project.service.utils.Pageable;

@CrossOrigin
@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	ContactService contactService;

	@PostMapping("")
	public ResponseEntity<Object> save(@Valid @RequestBody ContactDTO dto) {
		try {
			dto = contactService.add(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.CREATE_ERROR.getName());
		}
	}

	@PutMapping("")
	public ResponseEntity<Object> update(@Valid @RequestBody ContactDTO dto) {
		try {
			dto = contactService.update(dto);
			return ResponseEntity.ok(dto);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.UPDATE_ERROR.getName());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		try {
			ContactDTO dto = new ContactDTO();
			dto.setId(id);
			contactService.delete(dto);
			return ResponseEntity.noContent().build();

		} catch (DataIntegrityViolationException e) {
			return FactoryException.build(e, e.getClass().getSimpleName(),
					ContactErrorEnum.DELETE_ATTACHED_ERROR.getName(), ContactErrorEnum.DELETE_ERROR.getName());
		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.DELETE_ERROR.getName());
		}
	}

	@DeleteMapping("/people/{id}")
	public ResponseEntity<Object> removeByPerson(@PathVariable Long id) {
		try {
			ContactDTO dto = new ContactDTO();
			PersonDTO dtoPerson = new PersonDTO();
			dtoPerson.setId(id);
			dto.setPerson(dtoPerson);
			contactService.deleteByPerson(dto);
			return ResponseEntity.noContent().build();

		} catch (DataIntegrityViolationException e) {
			return FactoryException.build(e, e.getClass().getSimpleName(),
					ContactErrorEnum.DELETE_ATTACHED_ERROR.getName(), ContactErrorEnum.DELETE_ALL_ERROR.getName());
		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.DELETE_ALL_ERROR.getName());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id) {
		try {
			ContactDTO dto = contactService.findById(id);
			return ResponseEntity.ok(dto);
		
		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.GET_ERROR.getName());
		}
	}

	@GetMapping("")
	public ResponseEntity<Object> list(
			@RequestParam(value = "idPerson", required = true) int idPerson,
			@RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone) {
		try {
			Pageable list = contactService.findAllByPerson(offset, limit, idPerson, name, phone, email);
			return ResponseEntity.ok(list);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.LIST_ERROR.getName());
		}
	}

	@GetMapping("/people/{idPerson}")
	public ResponseEntity<Object> countByPerson(@PathVariable Long idPerson) {
		try {
			int total = contactService.countByPerson(idPerson);
			return ResponseEntity.ok(total);

		} catch (Exception e) {
			return FactoryException.build(e, e.getClass().getSimpleName(), e.getMessage(),
					ContactErrorEnum.LIST_ERROR.getName());
		}
	}

}

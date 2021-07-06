package br.com.elotech.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.elotech.project.converter.PersonConverter;
import br.com.elotech.project.dto.PersonDTO;
import br.com.elotech.project.model.Person;
import br.com.elotech.project.repository.PersonRepository;
import br.com.elotech.project.service.utils.Pageable;
import br.com.elotech.project.service.utils.ValidateBirthDate;
import br.com.elotech.project.service.utils.ValidateCpf;
import javassist.NotFoundException;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepo;

	private final String PERSON_NOTFOUND = "Pessoa não encontrada";


	public PersonDTO add(PersonDTO dto) throws IllegalAccessException, IllegalArgumentException, Exception {
		Person person = PersonConverter.dtoToModel(dto);
		long id = addPerson(person);
		dto.setId(id);
		return dto;
	}

	private long addPerson(Person person) throws IllegalAccessException, Exception {
		ValidateCpf.isCPF(person.getCpf());
		ValidateBirthDate.isBirthDate(person.getBirthDate());
		return personRepo.save(person).getId();
	}

	public PersonDTO findById(Long id) throws NotFoundException {
		Person person = personRepo.findById(id).orElseThrow(() -> new NotFoundException(PERSON_NOTFOUND));
		return PersonConverter.modelToDto(person);
	}
	
	private Person findById(PersonDTO dto) throws NotFoundException {
		Person person = personRepo.findById(dto.getId()).orElseThrow(() -> new NotFoundException(PERSON_NOTFOUND));
		return person;
	}

	public PersonDTO update(PersonDTO dto)
			throws IllegalAccessException, IllegalArgumentException, NotFoundException, Exception {
		Person model = findById(dto);
		if (!model.getCpf().equals(dto.getCpf()))
			ValidateCpf.isCPF(dto.getCpf());
		ValidateBirthDate.isBirthDate(model.getBirthDate());
		model = PersonConverter.dtoToModel(dto, model);
		return PersonConverter.modelToDto(personRepo.save(model));
	}

	public boolean delete(PersonDTO dto) throws IllegalAccessException, NotFoundException, Exception {
		Person model = findById(dto);
		personRepo.delete(model);
		Optional<Person> c = personRepo.findById(dto.getId());
		if (c.isPresent()) {
			return false;
		}
		return true;
	}
	
	public Pageable findAll(int offset, int limit, String name, String cpf) {
		org.springframework.data.domain.Pageable pageRequest = PageRequest.of(offset, limit, Sort.Direction.ASC, "name");
		List<Person> list = personRepo.findAllByNameContainingAndCpfContaining(name, cpf, pageRequest);
		long total = personRepo.countByNameContainingAndCpfContaining(name, cpf);

		return buildPagination(offset, limit, list, total);
	}

	private Pageable buildPagination(int offset, int limit, List<Person> list, long total) {
		List<PersonDTO> listDTO = list.stream().map(person -> PersonConverter.modelToDto(person)).collect(Collectors.toList());
		Pageable page = new Pageable(listDTO, listDTO.size(), total);
		page = page.build(limit, offset);
		return page;
	}

}

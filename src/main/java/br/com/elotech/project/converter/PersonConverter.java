package br.com.elotech.project.converter;

import java.util.Date;

import br.com.elotech.project.dto.PersonDTO;
import br.com.elotech.project.model.Person;

public abstract class PersonConverter {

	public static Person dtoToModel(PersonDTO dto) {
		Person model = new Person();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCpf(dto.getCpf());
		if (dto.getBirthDate() != null)
			model.setBirthDate(new Date(Long.parseLong(dto.getBirthDate())));
		return model;
	}

	public static Person dtoToModel(PersonDTO dto, Person model) {
		model.setId(dto.getId());
		if (dto.getName() != null && !dto.getName().isEmpty())
			model.setName(dto.getName());
		if (dto.getCpf() != null && !dto.getCpf().isEmpty())
			model.setCpf(dto.getCpf());
		if (dto.getBirthDate() != null)
			model.setBirthDate(new Date(Long.parseLong(dto.getBirthDate())));
		return model;
	}

	public static PersonDTO modelToDto(Person model) {
		PersonDTO dto = new PersonDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setCpf(model.getCpf());
		if (model.getBirthDate() != null)
			dto.setBirthDate(String.valueOf(model.getBirthDate().getTime()));
		return dto;
	}

}

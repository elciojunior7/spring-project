package br.com.elotech.project.converter;

import br.com.elotech.project.dto.ContactDTO;
import br.com.elotech.project.model.Contact;

public abstract class ContactConverter {

	public static Contact dtoToModel(ContactDTO dto) {
		Contact model = new Contact();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPhone(dto.getPhone());
		model.setEmail(dto.getEmail());
		if (dto.getPerson() != null)
			model.setPerson(PersonConverter.dtoToModel(dto.getPerson()));
		return model;
	}

	public static Contact dtoToModel(ContactDTO dto, Contact model) {
		model.setId(dto.getId());
		if (dto.getName() != null && !dto.getName().isEmpty())
			model.setName(dto.getName());
		if (dto.getPhone() != null && !dto.getPhone().isEmpty())
			model.setPhone(dto.getPhone());
		if (dto.getEmail() != null && !dto.getEmail().isEmpty())
			model.setEmail(dto.getEmail());
		if (dto.getPerson() != null)
			model.setPerson(PersonConverter.dtoToModel(dto.getPerson()));
		return model;
	}

	public static ContactDTO modelToDto(Contact model) {
		ContactDTO dto = new ContactDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPhone(model.getPhone());
		dto.setEmail(model.getEmail());
		if (model.getPerson() != null && model.getPerson().getName() != null)
			dto.setPerson(PersonConverter.modelToDto(model.getPerson()));
		return dto;
	}
	
	public static ContactDTO modelToDto(Contact model, ContactDTO dto) {
		model.setId(dto.getId());
		if (model.getName() != null && !model.getName().isEmpty())
			dto.setName(dto.getName());
		if (model.getPhone() != null && !model.getPhone().isEmpty())
			dto.setPhone(dto.getPhone());
		if (model.getEmail() != null && !model.getEmail().isEmpty())
			dto.setEmail(dto.getEmail());
		return dto;
	}


}

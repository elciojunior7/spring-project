package br.com.elotech.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elotech.project.converter.ContactConverter;
import br.com.elotech.project.dto.ContactDTO;
import br.com.elotech.project.model.Contact;
import br.com.elotech.project.repository.ContactRepository;
import br.com.elotech.project.service.utils.Pageable;
import br.com.elotech.project.service.utils.ValidateEmail;
import javassist.NotFoundException;

@Service
public class ContactService {

	@Autowired
	ContactRepository contactRepo;

	private final String CONTACT_NOTFOUND = "Contato não encontrado";


	public ContactDTO add(ContactDTO dto) throws IllegalAccessException, IllegalArgumentException, Exception {
		ValidateEmail.isValidEmailAddressRegex(dto.getEmail());
		Contact contact = ContactConverter.dtoToModel(dto);
		long id = addContact(contact);
		dto.setId(id);
		return dto;
	}

	public long addContact(Contact contact) throws IllegalAccessException, Exception {
		return contactRepo.save(contact).getId();
	}

	public ContactDTO findById(Long id) throws NotFoundException {
		Contact person = contactRepo.findById(id).orElseThrow(() -> new NotFoundException(CONTACT_NOTFOUND));
		return ContactConverter.modelToDto(person);
	}
	
	public Contact findById(ContactDTO dto) throws NotFoundException {
		Contact person = contactRepo.findById(dto.getId()).orElseThrow(() -> new NotFoundException(CONTACT_NOTFOUND));
		return person;
	}

	public ContactDTO update(ContactDTO dto)
			throws IllegalAccessException, IllegalArgumentException, NotFoundException, Exception {
		ValidateEmail.isValidEmailAddressRegex(dto.getEmail());
		Contact model = findById(dto);
		model = ContactConverter.dtoToModel(dto, model);
		return ContactConverter.modelToDto(contactRepo.save(model), dto);
	}

	public boolean delete(ContactDTO dto) throws IllegalAccessException, NotFoundException, Exception {
		Contact model = findById(dto);
		contactRepo.delete(model);
		Optional<Contact> c = contactRepo.findById(dto.getId());
		if (c.isPresent()) {
			return false;
		}
		return true;
	}
	
	public boolean deleteByPerson(ContactDTO dto) throws IllegalAccessException, NotFoundException, Exception {
		Contact model = findById(dto);
		contactRepo.deleteByPerson(model.getPerson());
		long total = contactRepo.countByPerson(model.getPerson());
		if (total > 0) {
			return false;
		}
		return true;
	}

	public Pageable findAllByPerson(int offset, int limit, long idPerson, String name, String phone, String email) {
		List<Contact> list = contactRepo.findByIdPersonAndNameAndPhoneAndEmail(offset, limit, idPerson,
				'%' + name + '%', '%' + phone + '%', '%' + email + '%');
		long total = contactRepo.countByIdPersonAndNameContainingAndPhoneContainingAndEmailContaining(idPerson, '%'+name+'%', '%'+phone+'%', '%'+email+'%');

		return buildPagination(offset, limit, list, total);
	}

	private Pageable buildPagination(int offset, int limit, List<Contact> list, long total) {
		List<ContactDTO> listDTO = list.stream().map(model -> ContactConverter.modelToDto(model)).collect(Collectors.toList());
		Pageable page = new Pageable(listDTO, listDTO.size(), total);
		page = page.build(limit, offset);
		return page;
	}

	public int countByPerson(Long idPerson) {
		return contactRepo.countByIdPerson(idPerson);
	}

}

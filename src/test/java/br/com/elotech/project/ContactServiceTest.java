package br.com.elotech.project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.elotech.project.dto.ContactDTO;
import br.com.elotech.project.service.ContactService;
import br.com.elotech.project.service.utils.Pageable;
import br.com.elotech.project.service.utils.ValidateEmail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {

	@Autowired
	ContactService contactService;

	ContactDTO hugo;
	ContactDTO joaquina;
	ContactDTO estela;

	@Before
	public void init() {
		hugo = new ContactDTO("Hugo", "9812344321", "elotech@elotech.com", 52);
		joaquina = new ContactDTO("Joaquina", "8798766789", "elotech.com", 52);
		estela = new ContactDTO("Estela", "5674188147", "elotech@gmail.com", 52);
	}

	@Test
	public void testAdd() {
		ContactDTO contact1 = null;
		try {
			contact1 = contactService.add(hugo);
		} catch (Exception e) {
		}
		Assert.assertNotNull(contact1);
	}

	@Test
	public void testUpdate() {
		ContactDTO contact1 = null;
		try {
			hugo.setId(7);
			hugo.setName("Hugo Santos");
			contact1 = contactService.update(hugo);
		} catch (Exception e) {
		}
		Assert.assertTrue(contact1.getName().equalsIgnoreCase("Hugo Santos"));
	}

	@Test
	public void testFindById() {
		ContactDTO contact1 = null;
		try {
			contact1 = contactService.findById(7L);
		} catch (Exception e) {
		}
		Assert.assertNotNull(contact1);
	}

	@Test
	public void testList() {
		Pageable list = null;
		try {
			list = contactService.findAllByPerson(0, 6, 52L, "Hu", "", "");
		} catch (Exception e) {
		}
		Assert.assertNotNull(list);
	}

	@Test
	public void testCountByPerson() {
		Integer total = null;
		try {
			total = contactService.countByPerson(52L);
		} catch (Exception e) {
		}
		Assert.assertNotNull(total);
	}

	@Test
	public void testEmailInvalidate() {
		boolean isEmail = false;
		try {
			isEmail = ValidateEmail.isValidEmailAddressRegex(joaquina.getEmail());
		} catch (Exception e) {
		}
		Assert.assertFalse(isEmail);
	}

	@Test
	public void testEmailValidate() {
		boolean isEmail = false;
		try {
			isEmail = ValidateEmail.isValidEmailAddressRegex(hugo.getEmail());
		} catch (Exception e) {
		}
		Assert.assertTrue(isEmail);
	}

}

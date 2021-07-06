package br.com.elotech.project;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.elotech.project.dto.PersonDTO;
import br.com.elotech.project.service.PersonService;
import br.com.elotech.project.service.utils.Pageable;
import br.com.elotech.project.service.utils.ValidateBirthDate;
import br.com.elotech.project.service.utils.ValidateCpf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	PersonService personService;

	PersonDTO joao;
	PersonDTO jose;
	PersonDTO maria;

	@Before
	public void init() {
		joao = new PersonDTO("João", "36278113058", "994330481000");
		jose = new PersonDTO("José", "34233283042", "888836081000"); // cpf correto 34233283041
		maria = new PersonDTO("Maria", "52880561027", "1290164081000");
	}

	@Test
	public void testAdd()
	{
		PersonDTO person1 = null;
		PersonDTO person2 = null;
		try {
			person1 = personService.add(joao);
			person2 = personService.add(jose);
		} catch (Exception e) {
		}
		Assert.assertNotNull(person1);
		Assert.assertNotNull(person2);
	}

	@Test
	public void testUpdate() {
		PersonDTO person = null;
		try {
			joao.setId(52);
			joao.setName("João Santos");
			person = personService.update(joao);
		} catch (Exception e) {
		}
		Assert.assertTrue(person.getName().equalsIgnoreCase("João Santos"));
	}

	@Test
	public void testFindById() {
		PersonDTO person = null;
		try {
			person = personService.findById(52L);
		} catch (Exception e) {
		}
		Assert.assertNotNull(person);
	}

	@Test
	public void testList() {
		Pageable list = null;
		try {
			list = personService.findAll(0, 6, "Jo", "");
		} catch (Exception e) {
		}
		Assert.assertNotNull(list);
	}

	@Test
	public void testCpfValidate() {
		boolean isCpf2 = false;
		try {
			isCpf2 = ValidateCpf.isCPF(maria.getCpf());
		} catch (Exception e) {
		}
		Assert.assertTrue(isCpf2);
	}

	@Test
	public void testBirthdateValidate() {
		boolean isBirthdate1 = false;
		boolean isBirthdate2 = false;
		try {
			isBirthdate1 = ValidateBirthDate.isBirthDate(new Date(Long.parseLong(jose.getBirthDate())));
			isBirthdate2 = ValidateBirthDate.isBirthDate(new Date(Long.parseLong(maria.getBirthDate())));
		} catch (Exception e) {
		}
		Assert.assertTrue(isBirthdate1);
		Assert.assertTrue(isBirthdate2);
	}

}

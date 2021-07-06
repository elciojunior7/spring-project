package br.com.elotech.project.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ContactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	@NotEmpty
	@Size(min = 2, max = 40)
	private String name;
	@NotEmpty
	@Size(min = 10, max = 11)
	private String phone;
	@NotEmpty
	@Size(min = 8)
	private String email;
	private PersonDTO person;

	public ContactDTO() { }

	public ContactDTO(String name, String phone, String email, long idPerson) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.person = new PersonDTO(idPerson);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

}

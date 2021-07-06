package br.com.elotech.project.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	@NotEmpty
	@Size(min = 2, max = 40)
	private String name;
	@NotBlank
	@NotEmpty
	@Size(min = 11, max = 11)
	private String cpf;
	@NotBlank
	@NotEmpty
	@Size(min = 8, max = 14)
	private String birthDate;
	private List<ContactDTO> contacts;

	public PersonDTO() { }

	public PersonDTO(String name, String cpf, String birthDate) {
		this.name = name;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

	public PersonDTO(long id) {
		this.id = id;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}
}

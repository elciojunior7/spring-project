package br.com.elotech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.elotech.project.model.Contact;
import br.com.elotech.project.model.Person;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	void deleteByPerson(Person person);

	long countByPerson(Person person);
	
	@Query(value = "SELECT * FROM contact c INNER JOIN person p ON c.person_id=p.id "
			+ "WHERE p.id=:idPerson AND c.name LIKE :name AND c.phone LIKE :phone AND c.email LIKE :email "
			+ "order by c.name LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Contact> findByIdPersonAndNameAndPhoneAndEmail(int offset, int limit, long idPerson, String name, String phone, String email);

	@Query(value = "SELECT count(*) FROM contact c INNER JOIN person p ON c.person_id=p.id "
			+ "WHERE p.id=:idPerson AND c.name LIKE :name AND c.phone LIKE :phone AND c.email LIKE :email", nativeQuery = true)
	long countByIdPersonAndNameContainingAndPhoneContainingAndEmailContaining(long idPerson, String name, String phone, String email);

	@Query(value = "SELECT count(*) FROM contact c INNER JOIN person p ON c.person_id=p.id WHERE p.id=:idPerson", nativeQuery = true)
	int countByIdPerson(Long idPerson);

}


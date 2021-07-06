package br.com.elotech.project.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elotech.project.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findAllByNameContainingAndCpfContaining(String name, String cpf, Pageable pageRequest);

	long countByNameContainingAndCpfContaining(String name, String cpf);

}
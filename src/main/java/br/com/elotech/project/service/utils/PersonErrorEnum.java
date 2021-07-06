package br.com.elotech.project.service.utils;

import java.util.stream.Stream;

public enum PersonErrorEnum {
	CREATE_ERROR("Ocorreu um erro durante o cadastro de pessoa"),
	UPDATE_ERROR("Ocorreu um erro durante a atualização de pessoa"),
	DELETE_ERROR("Ocorreu um erro durante a exclusão de pessoa"),
	DELETE_ATTACHED_ERROR("Não foi possível excluir registro. Pessoa está vinculada a outras informações"),
	GET_ERROR("Ocorreu um erro ao buscar pessoa"), 
	LIST_ERROR("Ocorreu um erro durante a listagem de pessoas");
 
	private String name;
 
	private PersonErrorEnum(String name) {
		this.name = name;
    }
 
	public String getName() {
		return name;
    }
 
	public static PersonErrorEnum of(String name) throws IllegalArgumentException {
		return Stream.of(PersonErrorEnum.values())
				.filter(p -> p.getName().equalsIgnoreCase(name))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}

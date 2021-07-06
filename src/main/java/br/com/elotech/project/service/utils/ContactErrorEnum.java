package br.com.elotech.project.service.utils;

import java.util.stream.Stream;

public enum ContactErrorEnum {
	CREATE_ERROR("Ocorreu um erro durante o cadastro de contato"),
	UPDATE_ERROR("Ocorreu um erro durante a atualização de contato"),
	DELETE_ERROR("Ocorreu um erro durante a exclusão de contato"),
	DELETE_ALL_ERROR("Ocorreu um erro durante a exclusão de múltiplos contatos"),
	DELETE_ATTACHED_ERROR("Não foi possível excluir registro. Contato está vinculado a outras informações"),
	GET_ERROR("Ocorreu um erro ao buscar contato"), 
	LIST_ERROR("Ocorreu um erro durante a listagem de contatos");
 
	private String name;
 
	private ContactErrorEnum(String name) {
		this.name = name;
    }
 
	public String getName() {
		return name;
    }
 
	public static ContactErrorEnum of(String name) throws IllegalArgumentException {
		return Stream.of(ContactErrorEnum.values())
				.filter(p -> p.getName().equalsIgnoreCase(name))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}

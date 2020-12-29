package api.dtos;

import api.entities.Associado;

public class AssociadoDto {

	private Long id;
	private String nome;
	private String cpf;
	
	public AssociadoDto() {
		// construtor padrão
	}
	
	public AssociadoDto(Associado associado) {
		this.id = associado.getId();
		this.nome = associado.getNome();
		this.cpf = associado.getCpf();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "AssociadoDto [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}
}

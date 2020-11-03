package api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class AssociadoDto {

	private Long id;
	private String nome;
	private String cpf;
	
	public AssociadoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "CPF não pode ser vazio.")
	@Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")	
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

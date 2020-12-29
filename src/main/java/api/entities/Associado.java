package api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import api.dtos.AssociadoDto;

@Entity
@Table(name = "associado")
public class Associado {
	
	private Long id;
	private String nome;
	private String cpf;
	
	public Associado() {
		// construtor padrão
	}
	
	public Associado(AssociadoDto associadoDto) {
		this.nome = associadoDto.getNome();
		this.cpf = associadoDto.getCpf();
	}
	
	public Associado(Long id) {
		this.id = id;
	}

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	@Column
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "CPF não pode ser vazio.")
	@Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")
	@Column
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Associado [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}	

}

package api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import api.dtos.AssociadoDto;

@Entity
@Table(name = "associado")
public class Associado {
	
	private Long id;
	private String nome;
	private String cpf;
	
	public Associado() {		
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

	@Column
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

package api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pauta")
public class Pauta {

	private Long id;
	private String nome;
	private String descricao;
	private LocalDateTime validaAte;
	private Boolean encerrada;

	public Pauta() {

	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "valida_ate")
	public LocalDateTime getValidaAte() {
		return validaAte;
	}

	public void setValidaAte(LocalDateTime validaAte) {
		this.validaAte = validaAte;
	}

	@Column(name = "encerrada")	
	public Boolean getEncerrada() {
		return encerrada;
	}

	public void setEncerrada(Boolean encerrada) {
		this.encerrada = encerrada;
	}
		
	@Override
	public String toString() {
		return "Pauta [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", validaAte=" + validaAte
				+ ", encerrada=" + encerrada + "]";
	}

}

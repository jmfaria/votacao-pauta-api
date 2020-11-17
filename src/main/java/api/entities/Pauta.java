package api.entities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import api.dtos.PautaDto;

@Entity
@Table(name = "pauta")
public class Pauta {

	private Long id;
	private String nome;
	private String descricao;
	private LocalDateTime validaAte;
	private Boolean encerrada = false;

	public Pauta() {

	}

	public Pauta(Long id) {
		this.id = id;
	}

	public Pauta(PautaDto pautaDto) {
		this.nome = pautaDto.getNome();
		this.descricao = pautaDto.getDescricao();
		this.setEncerrada(false);
	}
	
	public Pauta(Long id, Long tempoDaSessao) {

		this.id = id;
		this.validaAte = this.definirTempoDeSessao(tempoDaSessao);
	}	

	public Pauta(Long id, PautaDto pautaDto) {

		this.id = id;
		this.nome = pautaDto.getNome();
		this.descricao = pautaDto.getDescricao();
		this.setEncerrada(false);
		this.validaAte = this.definirTempoDeSessao(pautaDto.getTempoSessaoEmMinutos());
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

	private LocalDateTime definirTempoDeSessao(Long tempoDaSessao) {

		return (tempoDaSessao != null && tempoDaSessao > 0 ? LocalDateTime.now().plusMinutes(tempoDaSessao)
				: LocalDateTime.now().plusMinutes(1L));
	}

	@Transient
	public Long getTempoSessaoMinutos() {

		if (this.getValidaAte() != null) {
			return (this.getValidaAte().toEpochSecond(ZoneOffset.UTC)
					- LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) / 60;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Pauta [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", validaAte=" + validaAte
				+ ", encerrada=" + encerrada + "]";
	}

}

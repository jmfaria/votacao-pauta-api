package api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import api.dtos.VotacaoDto;

@Entity
@Table(name = "votacao")
public class Votacao {

	private Long id;
	private Pauta pauta;
	private Associado associado;
	private LocalDateTime votadoEm;
	private String voto;

	public Votacao() {
		// construtor padrão
	}
	
	public Votacao(VotacaoDto votacaoDto) {
		
		this.associado = new Associado(votacaoDto.getIdAssociado());
		this.pauta = new Pauta(votacaoDto.getIdPauta());
		this.voto = votacaoDto.getVoto().toUpperCase();
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

	@ManyToOne
	@JoinColumn(name = "pauta_id")
	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	@ManyToOne
	@JoinColumn(name = "associado_id")
	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	@Column(name = "votado_em")
	public LocalDateTime getVotadoEm() {
		return votadoEm;
	}

	public void setVotadoEm(LocalDateTime votadoEm) {
		this.votadoEm = votadoEm;
	}

	@PrePersist
	private void PrePersist() {
		this.votadoEm = LocalDateTime.now();
	}

	@Column(name = "voto")
	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	@Override
	public String toString() {
		return "Votacao [Id=" + id + ", pauta=" + pauta + ", associado=" + associado + ", votadoEm=" + votadoEm
				+ ", voto=" + voto + "]";
	}

}

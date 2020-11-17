package api.entities;

import java.time.LocalTime;

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

	private Long Id;
	private Pauta pauta;
	private Associado associado;
	private LocalTime votadoEm;
	private String voto;

	public Votacao() {
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
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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
	public LocalTime getVotadoEm() {
		return votadoEm;
	}

	public void setVotadoEm(LocalTime votadoEm) {
		this.votadoEm = votadoEm;
	}

	@PrePersist
	private void PrePersist() {
		this.votadoEm = LocalTime.now();
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
		return "Votacao [Id=" + Id + ", pauta=" + pauta + ", associado=" + associado + ", votadoEm=" + votadoEm
				+ ", voto=" + voto + "]";
	}

}

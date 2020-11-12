package api.entities;

import java.util.Calendar;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import api.dtos.VotacaoDto;

@Entity
@Table(name = "votacao")
public class Votacao {

	private Long Id;
	private Pauta pauta;
	private Associado associado;
	private Calendar votadoEm;
	private String voto;

	public Votacao() {
	}
	
	public Votacao(VotacaoDto votacaoDto, Optional<Associado> associado, Optional<Pauta> pauta) {
		
		this.associado = associado.get();
		this.pauta = pauta.get();
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "votado_em")
	public Calendar getVotadoEm() {
		return votadoEm;
	}

	public void setVotadoEm(Calendar votadoEm) {
		this.votadoEm = votadoEm;
	}

	@PrePersist
	private void PrePersist() {
		this.votadoEm = Calendar.getInstance();
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

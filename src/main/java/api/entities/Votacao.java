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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "votacao")
public class Votacao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "pauta_id")
	private Pauta pauta;
	
	@ManyToOne
	@JoinColumn(name = "associado_id")
	private Associado associado;
	
	@Column(name = "votado_em")
	private LocalDateTime votadoEm;
	
	@Column(name = "voto")
	private String voto;

	public Votacao(VotacaoDto votacaoDto) {
		
		this.associado = new Associado(votacaoDto.getIdAssociado());
		this.pauta = new Pauta(votacaoDto.getIdPauta());
		this.voto = votacaoDto.getVoto().toUpperCase();
	}

	@PrePersist
	private void prePersist() {
		this.votadoEm = LocalDateTime.now();
	}

}

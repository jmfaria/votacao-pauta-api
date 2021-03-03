package api.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

@Document(collection = "votacao")
public class Votacao {

	@Id
	private String id;
	
//	@ManyToOne
//	@JoinColumn(name = "pauta_id")
	private Pauta pauta;
	
//	@ManyToOne
//	@JoinColumn(name = "associado_id")
	private Associado associado;
	
//	@Column(name = "votado_em")
	private LocalDateTime votadoEm;
	
//	@Column(name = "voto")
	private String voto;

	public Votacao(VotacaoDto votacaoDto) {
		
		this.associado = new Associado(votacaoDto.getIdAssociado());
		this.pauta = new Pauta(votacaoDto.getIdPauta());
		this.voto = votacaoDto.getVoto().toUpperCase();
		this.votadoEm = LocalDateTime.now();
	}

//	@PrePersist
	private void prePersist() {
		this.votadoEm = LocalDateTime.now();
	}

}

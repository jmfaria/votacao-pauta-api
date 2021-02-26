package api.dtos;

import api.entities.Votacao;
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
public class VotacaoDto {
	
	private Long id;
	private Long idPauta;
	private Long idAssociado;	
	private String voto;
	
	public VotacaoDto(Votacao votacao) {
		this.id = votacao.getId();
		this.idPauta = votacao.getPauta().getId();
		this.idAssociado = votacao.getAssociado().getId();
		this.voto = votacao.getVoto();
	}

}

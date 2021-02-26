package api.dtos;

import api.entities.ResultadoVotacao;
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
public class ResultadoVotacaoDto {

	private long votosTotal;
	private long votosSim;
	private long votosNao;

	public ResultadoVotacaoDto(ResultadoVotacao resultadoVotacao) {

		if (resultadoVotacao != null) {
			this.votosSim = resultadoVotacao.getVotosSim();
			this.votosNao = resultadoVotacao.getVotosNao();
			this.votosTotal = resultadoVotacao.getVotosTotal();
		}
	}
}

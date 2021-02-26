package api.entities;

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
public class ResultadoVotacao {
	
	private Pauta pauta;
	private Long votosTotal;
	private Long votosSim;
	private Long votosNao;

}

package api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import api.entities.Pauta;
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
public class PautaDto {
	
	@JsonIgnore
	private String id;
	private String nome;
	private String descricao;
    @JsonIgnore
	private Long tempoSessaoEmMinutos;
	
	public PautaDto(Pauta pauta) {
		this.id = pauta.getId();
		this.nome = pauta.getNome();
		this.descricao = pauta.getDescricao();
		this.tempoSessaoEmMinutos = pauta.getTempoSessaoMinutos();		
	}
}

package api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import api.entities.Associado;
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
public class AssociadoDto {

	@JsonIgnore
	private String id;
	private String nome;
	private String cpf;
		
	public AssociadoDto(Associado associado) {
		this.id = associado.getId();
		this.nome = associado.getNome();
		this.cpf = associado.getCpf();
	}

}

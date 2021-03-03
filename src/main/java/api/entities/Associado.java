package api.entities;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import api.dtos.AssociadoDto;
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

@Document(collection = "associado")
public class Associado {
	
	@Id
	private String id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")
	private String cpf;
	
	public Associado(AssociadoDto associadoDto) {
		this.nome = associadoDto.getNome();
		this.cpf = associadoDto.getCpf();
	}
	
	public Associado(String id) {
		this.id = id;
	}

}

package api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
@Entity
@Table(name = "associado")
public class Associado {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	@Column
	private String nome;
	
	@NotEmpty(message = "CPF não pode ser vazio.")
	@Length(min = 11, max = 11, message = "CPF deve conter 11 caracteres.")
	@Column
	private String cpf;
	
	public Associado(AssociadoDto associadoDto) {
		this.nome = associadoDto.getNome();
		this.cpf = associadoDto.getCpf();
	}
	
	public Associado(Long id) {
		this.id = id;
	}

}

package api.entities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import api.dtos.PautaDto;
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
@Table(name = "pauta")
public class Pauta {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "valida_ate")
	private LocalDateTime validaAte;
	
	@Column(name = "encerrada")
	private Boolean encerrada = false;

	public Pauta(Long id) {
		this.id = id;
	}

	public Pauta(PautaDto pautaDto) {
		this.nome = pautaDto.getNome();
		this.descricao = pautaDto.getDescricao();
		this.setEncerrada(false);
	}
	
	public Pauta(Long id, Long tempoDaSessao) {

		this.id = id;
		this.validaAte = this.definirTempoDeSessao(tempoDaSessao);
	}	

	public Pauta(Long id, PautaDto pautaDto) {

		this.id = id;
		this.nome = pautaDto.getNome();
		this.descricao = pautaDto.getDescricao();
		this.setEncerrada(false);
		this.validaAte = this.definirTempoDeSessao(pautaDto.getTempoSessaoEmMinutos());
	}	
	
	private LocalDateTime definirTempoDeSessao(Long tempoDaSessao) {

		return (tempoDaSessao != null && tempoDaSessao > 0 ? LocalDateTime.now().plusMinutes(tempoDaSessao)
				: LocalDateTime.now().plusMinutes(1L));
	}

	@Transient
	public Long getTempoSessaoMinutos() {

		if (this.getValidaAte() != null) {
			return (this.getValidaAte().toEpochSecond(ZoneOffset.UTC)
					- LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) / 60;
		} else {
			return null;
		}
	}

}

package api.entities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
@Document(collection = "pauta")
public class Pauta {

	@Id
	private String id;
	private String nome;
	private String descricao;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime validaAte;
	private Boolean encerrada;

	public Pauta(String id) {
		this.id = id;
	}

	public Pauta(PautaDto pautaDto) {
		if(pautaDto.getId() != null)
			this.id = pautaDto.getId();
		this.nome = pautaDto.getNome();
		this.descricao = pautaDto.getDescricao();
		this.setEncerrada(false);
	}
	
	public Pauta(String id, Long tempoDaSessao) {

		this.id = id;
		this.validaAte = this.definirTempoDeSessao(tempoDaSessao);
	}	

	public Pauta(String id, PautaDto pautaDto) {

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
			
			Long tempoSessaoMinutos = (this.getValidaAte().toEpochSecond(ZoneOffset.UTC)
					- LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) / 60;
			return tempoSessaoMinutos > 0 ? tempoSessaoMinutos : 0;
					
		} else {
			return null;
		}
	}

}

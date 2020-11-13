package api.dtos;

import api.entities.ResultadoVotacao;

public class ResultadoVotacaoDto {
		
	private long votosTotal;
	private long votosSim;
	private long votosNao;
	
	public ResultadoVotacaoDto() {}
	
	public ResultadoVotacaoDto(ResultadoVotacao resultadoVotacao) {
		this.votosSim = resultadoVotacao.getVotosSim();
		this.votosNao = resultadoVotacao.getVotosNao();
		this.votosTotal = resultadoVotacao.getVotosTotal();
	}
	
	public Long getVotosSim() {
		return votosSim;
	}
	
	public void setVotosSim(Long votosSim) {
		this.votosSim = votosSim;
	}
	
	public Long getVotosNao() {
		return votosNao;
	}
	
	public void seVotostNao(Long votosNao) {
		this.votosNao = votosNao;
	}	

	public Long getVotosTotal() {
		return votosTotal;
	}

	public void setVotosTotal(Long votosTotal) {
		this.votosTotal = votosTotal;
	}

	public void setVotosNao(Long votosNao) {
		this.votosNao = votosNao;
	}
	
	@Override
	public String toString() {	
		return "Resultado: Total de votos: " + this.votosTotal 
				+ " Sim(" + this.votosSim + ") - Não(" + this.votosNao + ")";
	}

}

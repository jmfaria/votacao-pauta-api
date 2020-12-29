package api.dtos;

import api.entities.Votacao;

public class VotacaoDto {
	
	private Long id;
	private Long idPauta;
	private Long idAssociado;	
	private String voto;
	
	public VotacaoDto() {
		// construtor padrão		
	}
	
	public VotacaoDto(Votacao votacao) {
		this.id = votacao.getId();
		this.idPauta = votacao.getPauta().getId();
		this.idAssociado = votacao.getAssociado().getId();
		this.voto = votacao.getVoto();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
	public String getVoto() {
		return voto;
	}
	
	public void setVoto(String voto) {
		this.voto = voto;
	}
	
	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	@Override
	public String toString() {
		return "VotacaoDto [Id=" + id + ", IdPauta=" + idPauta + ", IdAssociado=" + idAssociado + ", voto=" + voto
				+ "]";
	}

}

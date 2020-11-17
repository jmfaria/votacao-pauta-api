package api.dtos;

import api.entities.Votacao;

public class VotacaoDto {
	
	private Long Id;
	private Long IdPauta;
	private Long IdAssociado;	
	private String voto;
	
	public VotacaoDto() {}
	
	public VotacaoDto(Votacao votacao) {
		this.Id = votacao.getId();
		this.IdPauta = votacao.getPauta().getId();
		this.IdAssociado = votacao.getAssociado().getId();
		this.voto = votacao.getVoto();
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}	
	
	public String getVoto() {
		return voto;
	}
	
	public void setVoto(String voto) {
		this.voto = voto;
	}
	
	public Long getIdPauta() {
		return IdPauta;
	}

	public void setIdPauta(Long idPauta) {
		IdPauta = idPauta;
	}

	public Long getIdAssociado() {
		return IdAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		IdAssociado = idAssociado;
	}

	@Override
	public String toString() {
		return "VotacaoDto [Id=" + Id + ", IdPauta=" + IdPauta + ", IdAssociado=" + IdAssociado + ", voto=" + voto
				+ "]";
	}

}

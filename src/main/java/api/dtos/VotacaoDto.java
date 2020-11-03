package api.dtos;

public class VotacaoDto {
	
	private Long Id;
	private Long IdPauta;
	private Long IdAssociado;	
	private String voto;
	
	public VotacaoDto() {}
	
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

}

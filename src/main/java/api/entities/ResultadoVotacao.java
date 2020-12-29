package api.entities;

public class ResultadoVotacao {
	
	private Pauta pauta;
	private Long votosTotal;
	private Long votosSim;
	private Long votosNao;
			
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

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}
	
	@Override
	public String toString() {	
		
		return 	"Finalizada a votação da Pauta \"" + this.pauta.getNome() + 	
				"\" -  Total de votos[" + this.votosTotal 
				+ "] - Sim[" + this.votosSim + "] - Não[" + this.votosNao + "]";
	}

}

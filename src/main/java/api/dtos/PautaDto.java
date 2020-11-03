package api.dtos;

public class PautaDto {
	
	private Long id;
	private String nome;
	private String descricao;
	private Long tempoSessaoEmMinutos;
	
	public PautaDto() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTempoSessaoEmMinutos() {
		return tempoSessaoEmMinutos;
	}

	public void setTempoSessaoEmMinutos(Long tempoSessaoEmMinutos) {
		this.tempoSessaoEmMinutos = tempoSessaoEmMinutos;
	}

	@Override
	public String toString() {
		return "PautaDto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", tempoSessaoEmMinutos="
				+ tempoSessaoEmMinutos + "]";
	}	
	
}

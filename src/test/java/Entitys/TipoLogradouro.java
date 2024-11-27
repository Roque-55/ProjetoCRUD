package Entitys;

public enum TipoLogradouro {
	Rua(1,"Rua"),
	Avenida(2,"Avenida"),
	Travessa(3,"Travessa"),
	Alameda(4,"Alameda"),
	Estrada(5,"Estrada"),
	Outro(6,"Outro");

	private String descricao;
	private int id;

	TipoLogradouro(int id, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao=descricao;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}
}

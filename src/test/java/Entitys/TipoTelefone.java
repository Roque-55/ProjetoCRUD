package Entitys;

public enum TipoTelefone {
	Celular(1,"Celular"),
	Residencial(2,"Residencial"),
	Comercial(3,"Comercial"),
	Outro(4,"Outro");

	private int id;
	private String descricao;

	TipoTelefone(int i, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
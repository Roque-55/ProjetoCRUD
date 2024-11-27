package Entitys;

public enum TipoEndereco {
	Residencial(1,"Residencial"),
	Cobranca(2,"Cobranca"),
	Entrega(3,"Entrega");

	private String descricao;
	private int id;

	TipoEndereco(int id, String descricao) {
		this.setId(id);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

}

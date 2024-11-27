package Entitys;

public enum TipoResidencia {
	Casa(1,"Casa"),
	Apartamento(2,"Apartamento"),
	Outro(3,"Outro");

	private String descricao;
	private int id;

	TipoResidencia(int id,String descricao) {
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

package Entitys;

public enum Genero {
	Feminino(1,"Feminino"),
	Masculino(2,"Masculino"),
	Outro(3,"Outro");

	private String descricao;
	private int id;

	Genero(int id, String descricao) {
		this.setId(id);
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

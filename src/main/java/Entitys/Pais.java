package Entitys;

public class Pais extends EntidadeDominio {
	private int id;
	private String nome;

	public Pais() {}

	public Pais(String input) {
		this.id = Integer.parseInt(input);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}

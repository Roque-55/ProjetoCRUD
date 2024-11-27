package Entitys;

public class Estado extends EntidadeDominio {
	private int id;
	private String nome;
	private Pais pais;

	public Estado(String input) {
		this.id = Integer.parseInt(input);
	}

	public Estado() {
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
}

package Controls;

import DAO.*;
import Entitys.*;
import Outros.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JavalinJackson;
import io.javalin.rendering.template.JavalinMustache;

import java.util.List;

public class ClienteController extends AbstractController {
	public String salvar(EntidadeDominio entidade) {
		return fachada.salvar(entidade);
	}
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		return fachada.consultar(entidade);
	}
	public String alterar(EntidadeDominio entidade) {
		return fachada.alterar(entidade);
	}
	public String excluir(EntidadeDominio entidade) {
		return fachada.excluir(entidade);
	}
	public EntidadeDominio selecionar(EntidadeDominio entidade) {
		return fachada.selecionar(entidade);
	}
	public String status(EntidadeDominio entidade) {
		return fachada.status(entidade);
	}

	public ClienteController() {
		Javalin app = Javalin.create(config -> {
			config.staticFiles.add("/static");
			config.fileRenderer(new JavalinMustache());

			// Configuração do ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			JavalinJackson.defaultMapper();
		}).start(7000);

		// Rotas
		configurarRotas(app);
	}

	private void configurarRotas(Javalin app) {
		app.get("/", ctx -> ctx.render("/static/html/index.html"));

		app.post("/login", this::realizaLogin);

		// Clientes
		app.get("/clientes/", this::carregaLista);
		app.get("/clientes/{pesquisa}", this::carregaListaPesquisa);
		app.get("/clientes/detalhes/{id}", this::detalhesCliente);
		app.patch("/clientes/{id}", this::alterarCliente);
		app.put("/clientes/", this::salvarCliente);
		app.delete("/clientes/{id}", this::excluirCliente);
		app.patch("/clientes/{id}/status", this::atualizarStatusCliente);

		// Telefones
		app.get("/telefones/{id}", this::detalhesTelefone);
		app.put("/telefones/", this::salvarTelefone);

		// Endereços
		app.get("/enderecos/seleciona/{id}", this::detalhesEndereco);
		app.put("/enderecos/{id}", this::salvarEndereco);
		app.patch("/enderecos/{id}", this::alterarEndereco);
		app.delete("/enderecos/{id}", this::excluirEndereco);
		app.get("/enderecos/{id}", this::listarEnderecos);

		// Cartões
		app.get("/cartoes/{id}", this::listarCartoes);
		app.put("/cartoes/{id}", this::salvarCartao);
		app.patch("/cartoes/{id}/preferencial", this::atualizarPreferencialCartao);
		app.delete("/cartoes/{id}", this::excluirCartao);

		// Países, Estados, Cidades
		app.get("/paises", this::listarPaises);
		app.get("/estados/{id}", this::listarEstados);
		app.get("/cidades/{id}", this::listarCidades);
	}

	private void realizaLogin(Context ctx) {
		Login login = ctx.bodyAsClass(Login.class);
		if (login.checkLogin()) {
			ctx.status(200).result("Usuário logado com sucesso!");
		} else {
			ctx.status(401).result("Credenciais inválidas.");
		}
	}

	private void carregaLista(Context ctx) {
		List<EntidadeDominio> listaClientes = getClientes();
		ctx.json(listaClientes);
	}

	private void carregaListaPesquisa(Context ctx) {
		String pesquisa = ctx.pathParam("pesquisa");
		List<EntidadeDominio> listaClientes = getClientes(pesquisa);
		ctx.json(listaClientes);
	}

	private List<EntidadeDominio> getClientes() {
		return consultar(new Cliente());
	}

	private List<EntidadeDominio> getClientes(String pesquisa) {
		return new ClienteDAO().consultarPesquisa(pesquisa);
	}

	private void detalhesCliente(Context ctx) {
		Cliente cliente = new Cliente();
		cliente.setId(Integer.parseInt(ctx.pathParam("id")));
		EntidadeDominio clienteDetalhes = selecionar(cliente);

		if (clienteDetalhes != null) {
			ctx.json(clienteDetalhes);
		} else {
			ctx.status(404).result("Cliente não encontrado");
		}
	}

	private void alterarCliente(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cliente cliente = ctx.bodyAsClass(Cliente.class);
		cliente.setId(id);
		alterar(cliente);
		ctx.status(200).result("Cliente alterado com sucesso!");
	}

	private void salvarCliente(Context ctx) {
		Cliente cliente = ctx.bodyAsClass(Cliente.class);
		String msg = salvar(cliente);

		if (msg != null && msg.matches("\\d+")) {
			ctx.status(200).result("Cliente salvo com sucesso! ID: " + msg);
		} else {
			ctx.status(400).result(msg);
		}
	}

	private void excluirCliente(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cliente cliente = new Cliente();
		cliente.setId(id);
		excluir(cliente);
		ctx.status(200).result("Cliente excluído com sucesso!");
	}

	private void atualizarStatusCliente(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		boolean novoStatus = Boolean.parseBoolean(ctx.body());

		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setStatus(novoStatus);
		status(cliente);
		ctx.status(200).result("Status atualizado com sucesso!");
	}

	private void detalhesTelefone(Context ctx) {
		Telefone telefone = new Telefone();
		telefone.setClienteId(Integer.parseInt(ctx.pathParam("id")));
		EntidadeDominio telefoneDetalhes = selecionar(telefone);

		if (telefoneDetalhes != null) {
			ctx.json(telefoneDetalhes);
		}
	}

	private void salvarTelefone(Context ctx) {
		Telefone telefone = ctx.bodyAsClass(Telefone.class);
		String msg = salvar(telefone);

		if (msg == null || msg.isEmpty()) {
			ctx.status(200).result("Telefone salvo com sucesso!");
		} else {
			ctx.status(400).result(msg);
		}
	}

	private void detalhesEndereco(Context ctx) {
		Endereco endereco = new Endereco();
		endereco.setId(Integer.parseInt(ctx.pathParam("id")));
		EntidadeDominio enderecoSelecionado = selecionar(endereco);

		if (enderecoSelecionado != null) {
			ctx.json(enderecoSelecionado);
		} else {
			ctx.status(404).result("Endereço não encontrado");
		}
	}

	private void salvarEndereco(Context ctx) {
		Endereco endereco = ctx.bodyAsClass(Endereco.class);
		endereco.setClienteId(Integer.parseInt(ctx.pathParam("id")));
		String msg = salvar(endereco);

		if (msg == null || msg.isEmpty()) {
			ctx.status(200).result("Endereço salvo com sucesso!");
		} else {
			ctx.status(400).result(msg);
		}
	}

	private void alterarEndereco(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Endereco endereco = ctx.bodyAsClass(Endereco.class);
		endereco.setId(id);
		alterar(endereco);
		ctx.status(200).result("Endereço alterado com sucesso!");
	}

	private void excluirEndereco(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Endereco endereco = new Endereco();
		endereco.setId(id);
		excluir(endereco);
		ctx.status(200).result("Endereço excluído com sucesso!");
	}

	private void listarEnderecos(Context ctx) {
		Endereco endereco = new Endereco();
		endereco.setClienteId(Integer.parseInt(ctx.pathParam("id")));
		List<EntidadeDominio> listaEnderecos = consultar(endereco);
		ctx.json(listaEnderecos);
	}

	private void listarCartoes(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cartao cartao = new Cartao();
		cartao.setClienteId(id);
		List<EntidadeDominio> listaCartoes = consultar(cartao);
		ctx.json(listaCartoes);
	}

	private void excluirCartao(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cartao cartao = new Cartao();
		cartao.setId(id);
		excluir(cartao);
		ctx.status(200).result("Cartão excluído com sucesso!");
	}

	private void salvarCartao(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cartao cartao = ctx.bodyAsClass(Cartao.class);
		cartao.setClienteId(id);
		String msg = salvar(cartao);

		if (msg == null || msg.isEmpty()) {
			ctx.status(200).result("Cartão salvo com sucesso!");
		} else {
			ctx.status(400).result(msg);
		}
	}

	private void atualizarPreferencialCartao(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		Cartao cartao = new Cartao();
		cartao.setId(id);
		status(cartao);
		ctx.status(200).result("Cartão preferencial selecionado com sucesso!");
	}

	private void listarPaises(Context ctx) {
		List<Pais> listaPaises = new PaisDAO().consultar();
		ctx.json(listaPaises);
	}

	private void listarEstados(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			List<Estado> listaEstados = new EstadoDAO().obterEstadosPorPais(id);
			ctx.json(listaEstados);
		} catch (NumberFormatException e) {
			ctx.status(404).result("Nenhum estado encontrado");
		}
	}

	private void listarCidades(Context ctx) {
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			List<Cidade> listaCidades = new CidadeDAO().obterCidadesPorEstado(id);
			ctx.json(listaCidades);
		} catch (NumberFormatException e) {
			ctx.status(404).result("Nenhuma cidade encontrada");
		}
	}
}

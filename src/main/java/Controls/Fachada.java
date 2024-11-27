package Controls;

import DAO.*;
import Entitys.*;
import Strategies.*;

import java.util.*;

public class Fachada implements IFachada {
	private Map<String, IDAO> daos;
	private Map<String, List<IStrategy>> strategies;

	public Fachada() {
		definirStrategies();
		definirDAOs();
	}

	private void definirStrategies() {
		strategies = new HashMap<String, List<IStrategy>>();

		ValidarCliente vCliente = new ValidarCliente();
		ValidarCPF vCPF = new ValidarCPF();
		ValidarTelefone vTelefone = new ValidarTelefone();
		ValidarCartao vCartao = new ValidarCartao();
		ValidarEndereco vEndereco = new ValidarEndereco();

		List<IStrategy> strategiesCliente = new ArrayList<IStrategy>();
		strategiesCliente.add(vCliente);
		strategiesCliente.add(vCPF);
		strategiesCliente.add(vTelefone);
		strategies.put(Cliente.class.getName(), strategiesCliente);

		List<IStrategy> strategiesTelefone = new ArrayList<IStrategy>();
		strategiesTelefone.add(vTelefone);
		strategies.put(Telefone.class.getName(), strategiesTelefone);

		List<IStrategy> strategiesEndereco = new ArrayList<IStrategy>();
		strategiesEndereco.add(vEndereco);
		strategies.put(Endereco.class.getName(), strategiesEndereco);

		List<IStrategy> strategiesCartao = new ArrayList<IStrategy>();
		strategiesCartao.add(vCartao);
		strategies.put(Cartao.class.getName(), strategiesCartao);
	}

	private void definirDAOs() {
		daos = new HashMap<String, IDAO>();
		daos.put(Cliente.class.getName(), new ClienteDAO());
		daos.put(Cartao.class.getName(), new CartaoDAO());
		daos.put(Endereco.class.getName(), new EnderecoDAO());
		daos.put(Telefone.class.getName(), new TelefoneDAO());
	}

	@Override
	public String salvar(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String metodo = stackTrace[2].getMethodName();

		String msg = executar(entidade);

		if(msg == null) {
			IDAO dao = daos.get(classe);
			LogDAO log = new LogDAO();
			log.salvaLog(new Date().toString(), classe + "." + metodo);
			return dao.salvar(entidade);
		}else {
			return msg;
		}
	}

	@Override
	public String alterar(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String metodo = stackTrace[2].getMethodName();

		IDAO dao = daos.get(classe);
		dao.alterar(entidade);
		LogDAO log = new LogDAO();
		log.salvaLog(new Date().toString(), classe + "." + metodo);
		return null;

	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		IDAO dao = daos.get(classe);
		return dao.consultar(entidade);
	}

	@Override
	public String excluir(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String metodo = stackTrace[2].getMethodName();
		IDAO dao = daos.get(classe);

		dao.excluir(entidade);
		LogDAO log = new LogDAO();
		log.salvaLog(new Date().toString(), classe + "." + metodo);
		return null;
	}

	public String status(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		IDAO dao = daos.get(classe);
		dao.status(entidade);
		return null;
	}

	public EntidadeDominio selecionar(EntidadeDominio entidade) {
		String classe = entidade.getClass().getName();
		IDAO dao = daos.get(classe);
		return dao.selecionar(entidade);
	}

	public String executar(EntidadeDominio entidade){
		String classe = entidade.getClass().getName();

		List<IStrategy> strategyEntidade = strategies.get(classe);

		StringBuilder sb = new StringBuilder();
		for(IStrategy s : strategyEntidade) {
			String msg = s.processar(entidade);
			if(msg != null) {
				sb.append(msg);
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		} else {
			return null;
		}
	}
}

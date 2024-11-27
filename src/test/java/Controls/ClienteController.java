package Controls;

import Entitys.EntidadeDominio;

import java.util.List;

public class ClienteController extends AbstractController{
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
}

package DAO;

import Entitys.EntidadeDominio;

import java.util.List;

public interface IDAO {
    public String salvar(EntidadeDominio entidade);
    public int alterar(EntidadeDominio entidade);
    public List<EntidadeDominio> consultar(EntidadeDominio entidade);
    public String excluir(EntidadeDominio entidade);
    public EntidadeDominio selecionar(EntidadeDominio entidade);
}

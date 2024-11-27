package Strategies;

import Entitys.Endereco;
import Entitys.EntidadeDominio;

public class ValidarEndereco implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        StringBuilder stringBuilder = new StringBuilder();

        if(entidade==null){
            return "Entidade nula";
        }

        if(entidade instanceof Endereco){
            Endereco endereco = (Endereco) entidade;

            if(endereco.getCep().isEmpty()){
                stringBuilder.append("Erro: CEP é obrigatório");
            }
            if(endereco.getLogradouro().isEmpty()){
                stringBuilder.append("Erro: Logradouro é obrigatório");
            }
            if(endereco.getBairro().isEmpty()){
                stringBuilder.append("Erro: Bairro é obrigatório");
            }
            return stringBuilder.toString();

        } else return "Erro ao validar";


    }
}

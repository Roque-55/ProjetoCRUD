package Strategies;

import Entitys.Cliente;
import Entitys.EntidadeDominio;

public class ValidarCliente implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        StringBuilder stringBuilder = new StringBuilder();
        if(entidade==null){
            return "Entidade nula";
        }
        if(entidade instanceof Cliente){
            Cliente cliente = (Cliente)entidade; //converte entidade para o tipo cliente
            if(cliente.getCpf()==null){
                stringBuilder.append("Erro: CPF é obrigatório");
            }
            if(cliente.getEmail()==null){
                stringBuilder.append("Erro: E-mail é obrigatório");
            }
            if(cliente.getNome()==null){
                stringBuilder.append("Erro: Nome é obrigatório");
            }
            if(cliente.getDataNascimento()==null){
                stringBuilder.append("Erro: Data de Nascimento é obrigatório");
            }
            if(cliente.getGenero()==null){
                stringBuilder.append("Erro: Genero é obrigatório");
            }
            if(cliente.getSenha()==null) {
                stringBuilder.append("Erro: Senha é obrigatória");
            }
            if(cliente.getSenha().length()<8) {
                System.out.println("menor que 8");
                stringBuilder.append("Erro: Senha precisa ser maior que 8 caracteres");
            }
            if (!cliente.getSenha().matches(".*[a-z].*")) {
                stringBuilder.append("Erro: A senha precisa conter pelo menos uma letra minúscula.\n");
            }
            if (!cliente.getSenha().matches(".*[A-Z].*")) {
                stringBuilder.append("Erro: A senha precisa conter pelo menos uma letra maiúscula.\n");
            }
            if (!cliente.getSenha().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                stringBuilder.append("Erro: A senha precisa conter pelo menos um caractere especial.\n");
            }
            if(!cliente.getEmail().contains("@")){
                stringBuilder.append("Erro: E-mail inválido");
            }
            if(!cliente.getSenha().contains(cliente.getConfirmarSenha())) {
                stringBuilder.append("Erro: A senha deve ser igual a confirmar senha");
            }
        }
        return stringBuilder.toString();
    }
}

package Controls;

import java.sql.SQLException;
import java.sql.Statement;

public class CriaBanco {
    private Statement stmt;

    {
        try {
            stmt = new Conexao().getConexao().createStatement();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar o banco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaEsquema() {
        criaDataBase();
        criaTabelaTelefone();
        criaTabelaPais();
        criaTabelaEstado();
        criaTabelaCidade();
        criaTabelaCliente();
        criaTabelaCartao();
        criaTabelaEndereco();
        criaTabelaLog();
        System.out.println("Esquema de banco de dados criado com sucesso");
    }

    public void criaDataBase() {
        try{
            stmt.execute("CREATE DATABASE IF NOT EXISTS PROJETO_CRUD");
        } catch (SQLException e) {
            System.out.println("Erro ao criar data base: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaTelefone(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.TELEFONE(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    DDD VARCHAR(3) NOT NULL,
                    NUMERO VARCHAR(9) NOT NULL,
                    TIPO_TELEFONE VARCHAR(20) NOT NULL)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela telefone: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaPais(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.PAIS(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    NOME VARCHAR(255) NOT NULL)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela pais: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaEstado(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.ESTADO(
                    ID      INTEGER PRIMARY KEY AUTO_INCREMENT,
                    NOME    VARCHAR(255) NOT NULL,
                    PAIS_ID INTEGER NOT NULL,
                    FOREIGN KEY (PAIS_ID) REFERENCES PROJETO_CRUD.PAIS (ID) ON DELETE CASCADE)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela estado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaCidade(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.CIDADE(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    NOME VARCHAR(255) NOT NULL,
                    ESTADO_ID INTEGER NOT NULL,
                    FOREIGN KEY (ESTADO_ID) REFERENCES PROJETO_CRUD.ESTADO (ID) ON DELETE CASCADE)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela cidade: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaCliente(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.CLIENTE(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    NOME VARCHAR(255) NOT NULL,
                    DATA_NASCIMENTO DATE NOT NULL,
                    CPF VARCHAR(11) NOT NULL,
                    GENERO VARCHAR(20) NOT NULL,
                    TELEFONE_ID INTEGER NOT NULL,
                    EMAIL VARCHAR(80) NOT NULL,
                    SENHA VARCHAR(255) NOT NULL,
                    STATUS BOOLEAN NOT NULL,
                    RANKING INTEGER NOT NULL,
                    FOREIGN KEY (TELEFONE_ID) REFERENCES PROJETO_CRUD.TELEFONE (ID))""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela cliente: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaCartao(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.CARTAO(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    CLIENTE_ID INTEGER NOT NULL,
                    NUMERO VARCHAR(20) NOT NULL,
                    NOME_IMPRESSO VARCHAR(255) NOT NULL,
                    CVV VARCHAR(3) NOT NULL,
                    PREFERENCIAL BOOLEAN NOT NULL,
                    BANDEIRA VARCHAR(20) NOT NULL,
                    FOREIGN KEY (CLIENTE_ID) REFERENCES PROJETO_CRUD.CLIENTE (ID) ON DELETE CASCADE)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela cartao: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaEndereco(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.ENDERECO(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    CLIENTE_ID INTEGER NOT NULL,
                    APELIDO VARCHAR(255) NOT NULL,
                    LOGRADOURO VARCHAR(255) NOT NULL,
                    NUMERO VARCHAR(10) NOT NULL,
                    CIDADE_ID INTEGER NOT NULL,
                    BAIRRO VARCHAR(255) NOT NULL,
                    CEP VARCHAR(8) NOT NULL,
                    OBSERVACOES VARCHAR(255),
                    TIPO_ENDERECO VARCHAR(20) NOT NULL,
                    TIPO_LOGRADOURO VARCHAR(20) NOT NULL,
                    TIPO_RESIDENCIA VARCHAR(20) NOT NULL,
                    FOREIGN KEY (CLIENTE_ID) REFERENCES PROJETO_CRUD.CLIENTE (ID) ON DELETE CASCADE,
                    FOREIGN KEY (CIDADE_ID) REFERENCES PROJETO_CRUD.CIDADE (ID) ON DELETE CASCADE)""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela endereco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void criaTabelaLog(){
        try{
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS PROJETO_CRUD.LOG(
                    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                    ENTIDADE VARCHAR(255) NOT NULL,
                    USER VARCHAR(255) NOT NULL,
                    DATA DATE NOT NULL);""");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela endereco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


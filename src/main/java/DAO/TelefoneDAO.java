package DAO;

import Controls.Conexao;
import Entitys.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO implements IDAO{
    public String salvar(EntidadeDominio entidade) {
        String sql = "INSERT INTO PROJETO_CRUD.TELEFONE(DDD, NUMERO, TIPO_TELEFONE, CLIENTE_ID) VALUES (?,?,?,?)";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            // criar uma conexao com o banco de dados
            conn = Conexao.getConexao();

            // foi criada uma prepareStatement para executar uma Query
            mysql = conn.prepareStatement(sql);

            // adicionar os valores que sao esperados pela query
            mysql.setString(1,((Telefone) entidade).getDdd());
            mysql.setString(2,((Telefone) entidade).getNumero());
            mysql.setString(3,((Telefone) entidade).getTipo().getDescricao());
            mysql.setInt(4,((Telefone) entidade).getClienteId());

            //	executar a query
            mysql.execute();

            System.out.println("Telefone salvo com sucesso");

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            // fechar as conexoes
            try {
                if(mysql!=null) {
                    mysql.close();
                }
                if(conn!=null) {
                    conn.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String status(EntidadeDominio entidade) {
        return "";
    }

    public int alterar(EntidadeDominio entidade) {
        return 0;
    }


    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        List<EntidadeDominio> telefones = new ArrayList<>();
        String sql = "SELECT DDD ,NUMERO , TIPO_TELEFONE " +
                "FROM PROJETO_CRUD.telefone WHERE cliente_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Telefone) entidade).getClienteId());

            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Telefone telefone = new Telefone();

                telefone.setDdd(rs.getString(1));
                telefone.setNumero(rs.getString(2));
                telefone.setTipo(TipoTelefone.valueOf(rs.getString(3)));
                telefones.add(telefone);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return telefones;
    }

    public String excluir(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public Telefone selecionar(EntidadeDominio entidade) {
        Telefone telefone = null;
        String sql = "SELECT DDD ,NUMERO , TIPO_TELEFONE " +
                "FROM PROJETO_CRUD.telefone WHERE cliente_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Telefone) entidade).getClienteId());

            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                telefone = new Telefone();
                telefone.setDdd(rs.getString(1));
                telefone.setNumero(rs.getString(2));
                telefone.setTipo(TipoTelefone.valueOf(rs.getString(3)));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return telefone;
    }

}

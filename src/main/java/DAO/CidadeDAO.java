package DAO;

import Entitys.Cidade;
import Controls.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO{
    public List<Cidade> listar() {
        List<Cidade> listaCidades = new ArrayList<>();
        String sql = "SELECT ID,NOME FROM PROJETO_CRUD.CIDADE";

        try{
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade();

                cidade.setId(rs.getInt(1));
                cidade.setNome(rs.getString(2));

                listaCidades.add(cidade);
            }
            conn.close();
        }catch(
                Exception e)
        {
            System.out.println();
        }
        return listaCidades;
    }

    public List<Cidade> obterCidadesPorEstado(int idEstado) {
        List<Cidade> cidades = new ArrayList<>();
        String sql = "SELECT ID, NOME FROM PROJETO_CRUD.CIDADE WHERE ESTADO_ID = ?";

        try{
            Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEstado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setId(rs.getInt("ID"));
                cidade.setNome(rs.getString("NOME"));

                cidades.add(cidade);
            }

            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return cidades;
    }
}


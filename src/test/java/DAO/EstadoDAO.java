package DAO;

import java.sql.Connection;
import Controls.Conexao;
import Entitys.Estado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {
    public List<Estado> consultar() {
        List<Estado> estados = new ArrayList<>();
        String sql = "SELECT ID,NOME FROM PROJETO_CRUD.ESTADO";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Estado estado = new Estado();

                estado.setId(rs.getInt(1));
                estado.setNome(rs.getString(2));

                estados.add(estado);
            }
            conn.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return estados;
    }

    public List<Estado> obterEstadosPorPais(String idPais) {
        List<Estado> estados = new ArrayList<>();
        String sql = "SELECT ID, NOME FROM PROJETO_CRUD.ESTADO WHERE PAIS_ID = ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPais);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Estado estado = new Estado();
                estado.setId(rs.getInt("ID"));
                estado.setNome(rs.getString("NOME"));

                estados.add(estado);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estados;
    }
}




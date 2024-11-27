package DAO;

import java.sql.Connection;
import Controls.Conexao;
import Entitys.Pais;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class PaisDAO {
    public List<Pais> consultar() {
        List<Pais> paises = new ArrayList<>();
        String sql = "SELECT ID, NOME FROM PROJETO_CRUD.PAIS";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setId(rs.getInt(1));
                pais.setNome(rs.getString(2));

                paises.add(pais);
            }
            conn.close();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return paises;
    }

}

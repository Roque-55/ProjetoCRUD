package Controls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "root");
        } catch (SQLException e) {
            System.out.println("Erro ao conectao ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    public void fecharConexao(Connection conexao) {
        Connection con = getConexao();
        try {
            if (con != null) {
                System.out.println("Conexao obtida com sucesso!");
                con.close();
            }
        }catch (SQLException e) {
            System.out.println("Erro ao fechar conexao: " + e.getMessage());
        }

    }
}

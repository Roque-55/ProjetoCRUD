package DAO;

import Controls.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LogDAO{
    public void salvaLog(String formattedDate, String entidade){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            try {
                conn = Conexao.getConexao();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(formattedDate + entidade);
            String sql = "INSERT INTO PROJETO_CRUD.LOG (ENTIDADE, USER, DATA) VALUES (?,?,?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, entidade);
            statement.setString(2, "admin");
            statement.setString(3, LocalDate.now().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

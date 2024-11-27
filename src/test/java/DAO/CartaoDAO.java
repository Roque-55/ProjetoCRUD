package DAO;

import Controls.Conexao;
import Entitys.BandeiraCartao;
import Entitys.Cartao;
import Entitys.EntidadeDominio;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartaoDAO implements IDAO{
    public String salvar(EntidadeDominio entidade) {
        String sql = "INSERT INTO projeto_crud.cartao(NUMERO,NOME_IMPRESSO,CVV,PREFERENCIAL,CLIENTE_ID,BANDEIRA) VALUES (?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement mysql = null;

        try {
            con = Conexao.getConexao();
            mysql = con.prepareStatement(sql);

            String numeroMascarado = mascaraNumeroCartao(((Cartao) entidade).getNumero());
            mysql.setString(1,numeroMascarado);
            mysql.setString(2,((Cartao) entidade).getNomeImpresso());

            String codSegurancaMascarado = mascaraCodSeguranca(((Cartao) entidade).getCvv());
            mysql.setString(3,codSegurancaMascarado);

            mysql.setBoolean(4,((Cartao)entidade).isPreferencial());
            mysql.setInt(5,((Cartao) entidade).getClienteId());
            mysql.setString(6,((Cartao) entidade).getBandeiraCartao().getDescricao());

            mysql.execute();

            System.out.println("Cartão salvo com sucesso");
            return "Cartão salvo com sucesso";

        }catch(Exception e){
            e.printStackTrace();
            return "Erro ao salvar o endereco: " + e.getMessage();
        }finally {
            if(con!=null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(mysql!=null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int alterar(EntidadeDominio entidade) {
        int status=0;
        String sql = "UPDATE projeto_crud.cartao SET NUMERO=?,"
                + "NOME_IMPRESSO=?, CVV=?, PREFERENCIAL=?, BANDEIRA=? WHERE id=?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);

            mysql.setString(1,((Cartao) entidade).getNumero());
            mysql.setString(2,((Cartao) entidade).getNomeImpresso());
            mysql.setString(3,((Cartao) entidade).getCvv());
            mysql.setBoolean(4,((Cartao) entidade).isPreferencial());
            mysql.setString(5,((Cartao) entidade).getBandeiraCartao().getDescricao());
            mysql.setInt(6,((Cartao) entidade).getId());	//id que vai ser atualizado
            status = mysql.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(mysql!=null) {
                    mysql.close();
                }
                if(conn!=null) {
                    conn.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return status;

    }

    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        List<EntidadeDominio> listaCartoes = new ArrayList<>();
        String sql = "SELECT ID ,NUMERO,NOME_IMPRESSO,CVV, PREFERENCIAL, BANDEIRA " +
                "FROM PROJETO_CRUD.CARTAO WHERE cliente_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Cartao) entidade).getClienteId());

            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Cartao cartao = new Cartao();

                cartao.setId(rs.getInt(1));
                cartao.setNumero(rs.getString(2));
                cartao.setNomeImpresso(rs.getString(3));
                cartao.setCvv(rs.getString(4));
                cartao.setPreferencial(rs.getBoolean(5));
                cartao.setBandeiraCartao(BandeiraCartao.valueOf(rs.getString(6)));
                listaCartoes.add(cartao);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCartoes;
    }


    public String excluir(EntidadeDominio entidade) {
        String sql = "DELETE from PROJETO_CRUD.CARTAO WHERE id = ?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);
            mysql.setInt(1, ((Cartao) entidade).getId());
            mysql.execute();

            System.out.println("Cartao excluído com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir o cartao: " + e.getMessage();
        } finally {
            try {
                if (mysql != null) {
                    mysql.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Cartao selecionar(EntidadeDominio entidade) {
        Cartao cartao = null;
        String sql = "SELECT * FROM PROJETO_CRUD.CARTAO WHERE id=?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Cartao) entidade).getId());
            ResultSet rs = mysql.executeQuery();

            while (rs.next()) {
                cartao = new Cartao();
                cartao.setId(rs.getInt(1));
                cartao.setNumero(rs.getString(2));
                cartao.setNomeImpresso(rs.getString(3));
                cartao.setCvv(rs.getString(4));
                cartao.setPreferencial(rs.getBoolean(5));
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        return cartao;
    }

    private String mascaraNumeroCartao(String numeroCartao) {
        int tamanho = numeroCartao.length();
        System.out.println(tamanho);
        String ultimosQuatroDigitos = numeroCartao.substring(tamanho - 4, tamanho);
        String mascara = "*".repeat(Math.max(0, tamanho - 4)) + ultimosQuatroDigitos;
        return mascara;
    }

    private String mascaraCodSeguranca(String codSeguranca) {
        int tamanho = codSeguranca.length();
        String mascara = "*".repeat(tamanho);
        return mascara;
    }
}
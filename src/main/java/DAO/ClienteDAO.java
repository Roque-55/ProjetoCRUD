package DAO;

import Entitys.*;
import Controls.Conexao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IDAO{

    public String salvar(EntidadeDominio entidade) {
        String clienteId = null;
        String sql = "INSERT INTO PROJETO_CRUD.CLIENTE (NOME, CPF, EMAIL, SENHA, GENERO, DATA_NASCIMENTO, STATUS, RANKING) VALUES (?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();

            mysql = conn.prepareStatement(sql);

            mysql.setString(1,((Cliente) entidade).getNome());
            mysql.setString(2,((Cliente) entidade).getCpf());
            mysql.setString(3,((Cliente) entidade).getEmail());
            String senhaSemHash = ((Cliente) entidade).getSenha();
            String senhaHash = senhaCriptografada(senhaSemHash);
            mysql.setString(4, senhaHash);
            mysql.setString(5,((Cliente) entidade).getGenero().getDescricao());
            mysql.setString(6,((Cliente) entidade).getDataNascimento().toString());
            mysql.setBoolean(7, true);
            mysql.setInt(8, ((Cliente) entidade).getRanking());

            mysql.execute();

            String req = "SELECT * from PROJETO_CRUD.CLIENTE where cpf = ?";
            mysql = conn.prepareStatement(req);
            mysql.setString(1,((Cliente) entidade).getCpf());

            ResultSet rs =  mysql.executeQuery();

            while (rs.next()) {
                clienteId = rs.getString(1);
            }

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
            }finally {
                if(conn!=null) {
                    try {
                        conn.close();
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
        System.out.println("Cliente de id:" + clienteId + " cadastrado com sucesso!");
        return clienteId;
    }

    public String status(EntidadeDominio entidade) {
        int status = 0;
        String sql = "UPDATE PROJETO_CRUD.CLIENTE SET STATUS=? WHERE ID=?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);
            mysql.setBoolean(1,((Cliente) entidade).isStatus());
            mysql.setInt(2,((Cliente) entidade).getId());
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
        System.out.println("status:" + status);
        return null;
    }

    public int alterar(EntidadeDominio entidade) {
        int status=0;
        String sql = "UPDATE PROJETO_CRUD.CLIENTE SET NOME=?,"
                + "CPF=?, EMAIL=?, GENERO=?, DATA_NASCIMENTO=?, RANKING=? WHERE ID=?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);

            mysql.setString(1,((Cliente) entidade).getNome());
            mysql.setString(2,((Cliente) entidade).getCpf());
            mysql.setString(3,((Cliente) entidade).getEmail());
            mysql.setString(4,((Cliente) entidade).getGenero().getDescricao());
            mysql.setString(5,((Cliente) entidade).getDataNascimento().toString());
            mysql.setInt(6,((Cliente) entidade).getRanking());
            mysql.setInt(7,((Cliente) entidade).getId());
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


    public String excluir(EntidadeDominio entidade) {
        String sql = "DELETE from PROJETO_CRUD.CLIENTE WHERE id = ?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);
            mysql.setInt(1, ((Cliente) entidade).getId());
            mysql.execute();
            System.out.println("Cliente excluído com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir o cliente: " + e.getMessage();
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

    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        List<EntidadeDominio> listaClientes = new ArrayList<>();
        String sql = "SELECT ID, NOME, CPF, EMAIL, GENERO, DATA_NASCIMENTO, STATUS, RANKING FROM PROJETO_CRUD.CLIENTE";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                Telefone telefone = new Telefone();

                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setEmail(rs.getString(4));
                cliente.setGenero(Genero.valueOf(rs.getString(5)));
                cliente.setDataNascimento(LocalDate.parse(rs.getString(6)));
                cliente.setStatus(rs.getBoolean(7));
                cliente.setRanking(rs.getInt(8));

                listaClientes.add(cliente);
            }
            conn.close();

        }catch(Exception e) {
            System.out.println();
        }
        return listaClientes;
    }

    public List<EntidadeDominio> consultarPesquisa(String pesquisa) {
        List<EntidadeDominio> listaClientes = new ArrayList<>();
        String sql = "SELECT ID, NOME, CPF, EMAIL, GENERO, DATA_NASCIMENTO, STATUS, RANKING" +
                " FROM PROJETO_CRUD.CLIENTE WHERE NOME LIKE ? OR CPF LIKE ? OR EMAIL LIKE ? OR GENERO LIKE ? OR DATA_NASCIMENTO LIKE ? OR RANKING LIKE ? OR ID LIKE ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setString(1, "%" + pesquisa + "%");
            mysql.setString(2, "%" + pesquisa + "%");
            mysql.setString(3, "%" + pesquisa + "%");
            mysql.setString(4, "%" + pesquisa + "%");
            mysql.setString(5, "%" + pesquisa + "%");
            mysql.setString(6, "%" + pesquisa + "%");
            mysql.setString(7, "%" + pesquisa + "%");
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                Telefone telefone = new Telefone();

                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setEmail(rs.getString(4));
                cliente.setGenero(Genero.valueOf(rs.getString(5)));
                cliente.setDataNascimento(LocalDate.parse(rs.getString(6)));
                cliente.setStatus(rs.getBoolean(7));
                cliente.setRanking(rs.getInt(8));

                listaClientes.add(cliente);
            }
            conn.close();

        }catch(Exception e) {
            System.out.println(e);
        }
        return listaClientes;
    }

    public Cliente selecionar(EntidadeDominio entidade) {
        Cliente cliente = null;
        String sql = "SELECT ID, NOME, CPF, EMAIL, GENERO, DATA_NASCIMENTO, STATUS, RANKING FROM PROJETO_CRUD.CLIENTE WHERE ID=?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Cliente) entidade).getId());
            ResultSet rs = mysql.executeQuery();

            while (rs.next()) {
                cliente = new Cliente();
                Telefone telefone = new Telefone();
                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setCpf(rs.getString(3));
                cliente.setEmail(rs.getString(4));
                cliente.setGenero(Genero.valueOf(rs.getString(5)));
                cliente.setDataNascimento(LocalDate.parse(rs.getString(6)));
                cliente.setStatus(rs.getBoolean(7));
                cliente.setRanking(rs.getInt(8));
            }
        }catch(Exception e) {
            System.out.println(e);
        }

        return cliente;
    }

    public int alterarSenha(int id, String novaSenha) throws Exception {
        int status = 0;
        String sql = "UPDATE PROJETO_CRUD.CLIENTE SET SENHA = ? WHERE ID = ?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);

            String senhaHash = senhaCriptografada(novaSenha);
            mysql.setString(1, senhaHash);
            mysql.setInt(2,id);

            status = mysql.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (mysql != null) {
                    mysql.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    private String senhaCriptografada(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());

            // Converte o hash de bytes para uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Lide com exceções ou propague-as conforme necessário
            return null;
        }
    }
}

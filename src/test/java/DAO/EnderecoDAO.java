package DAO;

import Controls.Conexao;
import Entitys.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements IDAO{
    public String salvar(EntidadeDominio entidade) {
        String sql = "INSERT INTO PROJETO_CRUD.ENDERECO(APELIDO, CEP, LOGRADOURO, NUMERO, BAIRRO, CLIENTE_ID, TIPO_ENDERECO, TIPO_LOGRADOURO, TIPO_RESIDENCIA, CIDADE_ID, OBSERVACOES) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();

            mysql = conn.prepareStatement(sql);

            mysql.setString(1,((Endereco)entidade).getApelido());
            mysql.setString(2,((Endereco)entidade).getCep());
            mysql.setString(3,((Endereco)entidade).getLogradouro());
            mysql.setInt(4,((Endereco)entidade).getNumero());
            mysql.setString(5,((Endereco)entidade).getBairro());
            mysql.setInt(6,((Endereco)entidade).getClienteId());
            mysql.setString(7,((Endereco)entidade).getTipoEndereco().getDescricao());
            mysql.setString(8,((Endereco)entidade).getTipoLogradouro().getDescricao());
            mysql.setString(9,((Endereco)entidade).getTipoResidencia().getDescricao());
            mysql.setInt(10,((Endereco)entidade).getCidade().getId());
            mysql.setString(11,((Endereco)entidade).getObservacoes());

            mysql.execute();

            System.out.println("Endereço salvo com sucesso");
            return "Endereço salvo com sucesso";

        }catch(Exception e){
            e.printStackTrace();
            return "Erro ao salvar o endereco: " + e.getMessage();
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
    }

    public int alterar(EntidadeDominio entidade) {
        int status=0;
        String sql = "UPDATE PROJETO_CRUD.ENDERECO SET CEP=?, LOGRADOURO=?, NUMERO=?, BAIRRO=?, CLIENTE_ID=?, TIPO_ENDERECO=?, TIPO_LOGRADOURO=?, TIPO_RESIDENCIA=?, OBSERVACOES=? WHERE id=?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);

            mysql.setString(1,((Endereco)entidade).getCep());
            mysql.setString(2,((Endereco)entidade).getLogradouro());
            mysql.setInt(3,((Endereco)entidade).getNumero());
            mysql.setString(4,((Endereco)entidade).getBairro());
            mysql.setInt(5,((Endereco)entidade).getClienteId());
            mysql.setString(6,((Endereco)entidade).getTipoEndereco().getDescricao());
            mysql.setString(7,((Endereco)entidade).getTipoLogradouro().getDescricao());
            mysql.setString(8,((Endereco)entidade).getTipoResidencia().getDescricao());
            mysql.setString(9,((Endereco)entidade).getObservacoes());
            mysql.setInt(10,((Endereco)entidade).getId());
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
        List<EntidadeDominio> listaEnderecos = new ArrayList<>();
        String sql = "SELECT *  FROM PROJETO_CRUD.ENDERECO a JOIN PROJETO_CRUD.CIDADE b ON a.CIDADE_ID = b.ID JOIN PROJETO_CRUD.ESTADO c ON b.ESTADO_ID = c.ID JOIN PROJETO_CRUD.PAIS d ON c.PAIS_ID = d.ID WHERE CLIENTE_ID = ?";

        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Endereco) entidade).getClienteId());

            ResultSet rs = mysql.executeQuery();

            while (rs.next()) {
                Endereco endereco = new Endereco();
                Cidade cidade = new Cidade();
                Estado estado = new Estado();
                Pais pais = new Pais();
                endereco.setId(rs.getInt(1));
                endereco.setApelido(rs.getString(3));
                endereco.setLogradouro(rs.getString(4));
                endereco.setNumero(rs.getInt(5));
                endereco.setBairro(rs.getString(7));
                endereco.setCep(rs.getString(8));
                endereco.setObservacoes(rs.getString(9));
                endereco.setTipoEndereco(TipoEndereco.valueOf(rs.getString(10)));
                endereco.setTipoLogradouro(TipoLogradouro.valueOf(rs.getString(11)));
                endereco.setTipoResidencia(TipoResidencia.valueOf(rs.getString(12)));

                cidade.setId(rs.getInt(13));
                cidade.setNome(rs.getString(14));

                estado.setId(rs.getInt(16));
                estado.setNome(rs.getString(17));

                pais.setId(rs.getInt(19));
                pais.setNome(rs.getString(20));

                estado.setPais(pais);
                cidade.setEstado(estado);
                endereco.setCidade(cidade);

                listaEnderecos.add(endereco);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEnderecos;
    }


    public String excluir(EntidadeDominio entidade) {
        String sql = "DELETE from PROJETO_CRUD.ENDERECO WHERE id = ?";
        Connection conn = null;
        PreparedStatement mysql = null;

        try {
            conn = Conexao.getConexao();
            mysql = conn.prepareStatement(sql);
            mysql.setInt(1, ((Endereco) entidade).getId());
            mysql.execute();

            System.out.println("Endereco excluído com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir o endereco: " + e.getMessage();
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
    public EntidadeDominio selecionar(EntidadeDominio entidade) {
        Endereco endereco = null;
        String sql = "\n" +
                "SELECT * FROM PROJETO_CRUD.ENDERECO a JOIN PROJETO_CRUD.CIDADE b ON a.CIDADE_ID = b.ID JOIN PROJETO_CRUD.ESTADO c ON b.ESTADO_ID = c.ID JOIN PROJETO_CRUD.PAIS d ON c.PAIS_ID = d.ID WHERE ID = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement mysql = conn.prepareStatement(sql);
            mysql.setInt(1,((Endereco) entidade).getId());
            ResultSet rs = mysql.executeQuery();

            while (rs.next()) {
                endereco = new Endereco();
                Cidade cidade = new Cidade();
                Estado estado = new Estado();
                Pais pais = new Pais();
                endereco.setId(rs.getInt(1));
                endereco.setClienteId(rs.getInt(2));
                endereco.setApelido(rs.getString(3));
                endereco.setLogradouro(rs.getString(4));
                endereco.setNumero(rs.getInt(5));
                endereco.setBairro(rs.getString(7));
                endereco.setCep(rs.getString(8));
                endereco.setObservacoes(rs.getString(9));
                endereco.setTipoEndereco(TipoEndereco.valueOf(rs.getString(10)));
                endereco.setTipoLogradouro(TipoLogradouro.valueOf(rs.getString(11)));
                endereco.setTipoResidencia(TipoResidencia.valueOf(rs.getString(12)));

                cidade.setId(rs.getInt(13));
                cidade.setNome(rs.getString(14));

                estado.setId(rs.getInt(16));
                estado.setNome(rs.getString(17));

                pais.setId(rs.getInt(19));
                pais.setNome(rs.getString(20));

                estado.setPais(pais);
                cidade.setEstado(estado);
                endereco.setCidade(cidade);
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        System.out.println(endereco);
        return endereco;
    }

}


package br.com.fiap.dao;


import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Parente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ParenteDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Parente parente) throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_tdspw_parente (cd_parente, nm_parente," +
                            "ds_parentesco, nr_telefone, cd_paciente) values (sq_tdspw_parente.nextval, ?,?,?,?)",
                    new String[]{"cd_paciente"});
            setarParametros(parente, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                parente.setCodigo(rs.getInt(1));
            }
        }
    }

    public List<Parente> listar() throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_parente");
            ResultSet rs = stmt.executeQuery();
            List <Parente> lista = new ArrayList<>();
            while(rs.next()){
                Parente parente = parseParente(rs);
                lista.add(parente);
            }
            return lista;
        }
    }

    public void atualizar(Parente parente) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_tdspw_parente set nm_parente = ?," +
                    "ds_parentesco = ?, nr_telefone = ?, cd_paciente = ? where cd_parente = ?");
            setarParametros(parente, stmt);
            stmt.setInt(5, parente.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum parente para atualizar!");
        }
    }

    public Parente buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("Select * from t_tdspw_parente where cd_parente = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new EntidadeNaoEncontrada("Parente não encontrado, ID não existe!");
            return parseParente(rs);
        }
    }

    public List<Parente> buscarPorPaciente(int codigoPaciente) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_parente where cd_paciente = ?");
            stmt.setInt(1, codigoPaciente);
            ResultSet rs = stmt.executeQuery();
            List<Parente> lista = new ArrayList<>();
            while (rs.next()) {
                Parente parente = parseParente(rs);
                lista.add(parente);
            }
            return lista;
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_tdspw_parente where cd_parente = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O parente não existe!");
        }
    }

    private static void setarParametros(Parente parente, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, parente.getNome());
        stmt.setString(2, parente.getDsParentesco());
        stmt.setString(3, parente.getNmrTelefone());
        stmt.setInt(4, parente.getCodigoPaciente());
    }

    private static Parente parseParente(ResultSet result) throws SQLException{
        int codigo = result.getInt("cd_parente");
        String nome = result.getString("nm_parente");
        String dsParentesco = result.getString("ds_parentesco");
        String nmrTelefone = result.getString("nr_telefone");
        int codigoPaciente = result.getInt("cd_paciente");
        return new Parente(codigo, nome, dsParentesco, nmrTelefone, codigoPaciente);
    }
}

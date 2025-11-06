package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Lembrete;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LembreteDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Lembrete lembrete) throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_tdspw_lembrete (cd_lembrete, ds_mensagem," +
                            "dt_envio, cd_parente) values " +
                            "(sq_tdspw_lembrete.nextval, ?,?,?)",
                    new String[]{"cd_lembrete"});
            setarParametros(lembrete, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                lembrete.setCodigo(rs.getInt(1));
            }
        }
    }

    public List<Lembrete> listar() throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_lembrete");
            ResultSet rs = stmt.executeQuery();
            List <Lembrete> lista = new ArrayList<>();
            while(rs.next()){
                Lembrete lembrete = parseLembrete(rs);
                lista.add(lembrete);
            }
            return lista;
        }
    }

    public void atualizar(Lembrete lembrete) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_tdspw_lembrete set ds_mensagem = ?," +
                    "dt_envio = ?, cd_parente = ? where cd_lembrete = ?");
            setarParametros(lembrete, stmt);
            stmt.setInt(4, lembrete.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum lembrete para atualizar!");
        }
    }

    public Lembrete buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("Select * from t_tdspw_lembrete where cd_lembrete = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new EntidadeNaoEncontrada("Lembrete não encontrado, ID não existe!");
            return parseLembrete(rs);
        }
    }
    public List<Lembrete> buscarPorParente(int codigoParente) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_lembrete where cd_parente = ?");
            stmt.setInt(1, codigoParente);
            ResultSet rs = stmt.executeQuery();
            List<Lembrete> lista = new ArrayList<>();
            while (rs.next()) {
                Lembrete lembrete = parseLembrete(rs);
                lista.add(lembrete);
            }
            return lista;
        }
    }

    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_tdspw_lembrete where cd_lembrete = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O lembrete não existe!");
        }
    }

    private static void setarParametros(Lembrete lembrete, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, lembrete.getMensagem());
        stmt.setObject(2,lembrete.getDataEnvio());
        stmt.setInt(3, lembrete.getCodigoParente());
    }

    private static Lembrete parseLembrete(ResultSet result) throws SQLException{
        int codigo = result.getInt("cd_lembrete");
        String mensagem = result.getString("ds_mensagem");
        LocalDate dataEnvio = result.getDate("dt_envio").toLocalDate();
        int codigoParente = result.getInt("cd_parente");
        return new Lembrete(codigo, mensagem, dataEnvio, codigoParente);
    }
}

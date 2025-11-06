package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.Medico;
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
public class ConsultaDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Consulta consulta) throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_tdspw_consulta (cd_consulta, dt_inicio," +
                            "dt_fim, ds_link, ds_observacoes, ds_status, cd_paciente, cd_medico) values " +
                            "(sq_tdspw_consulta.nextval, ?,?,?,?,?,?,?)",
                    new String[]{"cd_consulta"});
            setarParametros(consulta, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                consulta.setCodigo(rs.getInt(1));
            }
        }
    }

    public List<Consulta> listar() throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_consulta");
            ResultSet rs = stmt.executeQuery();
            List <Consulta> lista = new ArrayList<>();
            while(rs.next()){
                 Consulta consulta = parseConsulta(rs);
                lista.add(consulta);
            }
            return lista;
        }
    }

    public void atualizar(Consulta consulta) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_tdspw_consulta set dt_inicio = ?," +
                    "dt_fim = ?, ds_link = ?, ds_observacoes = ?, ds_status = ?, cd_paciente = ?, cd_medico = ? where cd_consulta = ?");
            setarParametros(consulta, stmt);
            stmt.setInt(8, consulta.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhuma consulta para atualizar!");
        }
    }

    public Consulta buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("Select * from t_tdspw_consulta where cd_consulta = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new EntidadeNaoEncontrada("Consulta não encontrada, ID não existe!");
            return parseConsulta(rs);
        }
    }

    public List<Consulta> buscarPorPaciente(int codigoPaciente) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_consulta where cd_paciente = ?");
            stmt.setInt(1, codigoPaciente);
            ResultSet rs = stmt.executeQuery();
            List<Consulta> lista = new ArrayList<>();
            while (rs.next()) {
                Consulta consulta = parseConsulta(rs);
                lista.add(consulta);
            }
            return lista;
        }
    }

    public List<Consulta> buscarPorMedico(int codigoMedico) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_consulta where cd_medico = ?");
            stmt.setInt(1, codigoMedico);
            ResultSet rs = stmt.executeQuery();
            List<Consulta> lista = new ArrayList<>();
            while (rs.next()) {
                Consulta consulta = parseConsulta(rs);
                lista.add(consulta);
            }
            return lista;
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_tdspw_consulta where cd_consulta = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. A consulta não existe!");
        }
    }

    private static void setarParametros(Consulta consulta, PreparedStatement stmt) throws SQLException{
        stmt.setObject(1, consulta.getDataInicio());
        stmt.setObject(2, consulta.getDataFim());
        stmt.setString(3, consulta.getLink());
        stmt.setString(4, consulta.getObservacoes());
        stmt.setString(5, consulta.getStatusConsulta());
        stmt.setInt(6, consulta.getCodigoPaciente());
        stmt.setInt(7, consulta.getCodigoMedico());
    }

    private static Consulta parseConsulta(ResultSet result) throws SQLException {
        int codigo = result.getInt("cd_consulta");
        LocalDate dataInicio = result.getDate("dt_inicio").toLocalDate();
        LocalDate dataFim = result.getDate("dt_fim").toLocalDate();
        String link = result.getString("ds_link");
        String observacoes = result.getString("ds_observacoes");
        String statusConsulta = result.getString("ds_status");
        int codigoPaciente = result.getInt("cd_paciente");
        int codigoMedico = result.getInt("cd_medico");
        return new Consulta(codigo, dataInicio, dataFim, link, observacoes, statusConsulta, codigoPaciente, codigoMedico);
    }
}

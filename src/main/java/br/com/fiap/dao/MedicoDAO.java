package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
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
public class MedicoDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Medico medico) throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_tdspw_medico (cd_medico, nm_medico," +
                            "dt_nascimento, nr_crm, ds_especialidade, vl_salario) values (sq_tdspw_medico.nextval, ?,?,?,?,?)",
                    new String[]{"cd_medico"});
            setarParametros(medico, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                medico.setCodigo(rs.getInt(1));
            }
        }
    }

    public List<Medico> listar() throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_medico");
            ResultSet rs = stmt.executeQuery();
            List <Medico> lista = new ArrayList<>();
            while(rs.next()){
                Medico medico = parseMedico(rs);
                lista.add(medico);
            }
            return lista;
        }
    }
    public Medico buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("Select * from t_tdspw_medico where cd_medico = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new EntidadeNaoEncontrada("Médico(a) não encontrado, ID não existe!");
            return parseMedico(rs);

        }

    }

    public void atualizar(Medico medico) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_tdspw_medico set nm_medico = ?," +
                    "dt_nascimento = ?, nr_crm = ?, ds_especialidade = ?, vl_salario = ? where cd_medico = ?");
            setarParametros(medico, stmt);
            stmt.setInt(6, medico.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum médico para atualizar!");
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_tdspw_medico where cd_medico = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O médico não existe!");
        }
    }

    private static void setarParametros(Medico medico, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, medico.getNome());
        stmt.setObject(2, medico.getDataNascimento());
        stmt.setString(3, medico.getCrm());
        stmt.setString(4, medico.getEspecialidade());
        stmt.setDouble(5, medico.getSalario());
    }

    private static Medico parseMedico(ResultSet result) throws SQLException {
        int codigoMedico = result.getInt("cd_medico");
        String nome = result.getString("nm_medico");
        LocalDate dataNascimento = result.getDate("dt_nascimento").toLocalDate();
        String crm = result.getString("nr_crm");
        String especialidade = result.getString("ds_especialidade");
        double salario = result.getDouble("vl_salario");
        return new Medico(nome, dataNascimento, codigoMedico, crm, especialidade, salario);
    }
}

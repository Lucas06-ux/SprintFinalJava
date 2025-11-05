package br.com.fiap.dao;

import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteDAO {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Paciente paciente) throws SQLException{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert into t_tdspw_paciente (cd_paciente, nm_paciente," +
                    "dt_nascimento, nr_cpf, ds_email, nr_telefone) values (sq_tdspw_paciente.nextval, ?,?,?,?,?)",
                    new String[]{"cd_paciente"});
            setarParametros(paciente, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                paciente.setCodigo(rs.getInt(1));
            }
        }
    }


    public List<Paciente> listar() throws SQLException {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("select * from t_tdspw_paciente");
            ResultSet rs = stmt.executeQuery();
            List <Paciente> lista = new ArrayList<>();
            while(rs.next()){
                Paciente paciente = parsePaciente(rs);
                lista.add(paciente);
            }
            return lista;
        }
    }// listar

    public Paciente buscar(int codigo) throws SQLException, EntidadeNaoEncontrada {
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("Select * from t_tdspw_paciente where cd_paciente = ?");
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new EntidadeNaoEncontrada("Paciente não encontrado, ID não existe!");
            return parsePaciente(rs);

        }

    }
    public void atualizar(Paciente paciente) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("update t_tdspw_paciente set nm_paciente = ?," +
                    "dt_nascimento = ?, nr_cpf = ?, ds_email = ?, nr_telefone = ? where cd_paciente = ?");
            setarParametros(paciente, stmt);
            stmt.setInt(6, paciente.getCodigo());

            if(stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontrada("Nenhum paciente para atualizar!");
        }
    }
    public void remover(int codigo) throws SQLException, EntidadeNaoEncontrada{
        try(Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("delete from t_tdspw_paciente where cd_paciente = ?");
            stmt.setInt(1, codigo);
            int linha = stmt.executeUpdate();
            if (linha == 0)
                throw new EntidadeNaoEncontrada("Não foi possivel remover. O(a) paciente não existe!");
        }
    }

    private static void setarParametros(Paciente paciente, PreparedStatement stmt) throws SQLException{
        stmt.setString(1, paciente.getNome());
        stmt.setObject(2, paciente.getDataNascimento());
        stmt.setString(3, paciente.getCpf());
        stmt.setString(4, paciente.getEmail());
        stmt.setString(5, paciente.getNmrTelefone());
    }

    private static Paciente parsePaciente(ResultSet result) throws SQLException {
        int codigo = result.getInt("cd_paciente");
        String nome = result.getString("nm_paciente");
        LocalDate dataNascimento = result.getDate("dt_nascimento").toLocalDate();
        String cpf = result.getString("nr_cpf");
        String email = result.getString("ds_email");
        String nmrTelefone = result.getString("nr_telefone");
        return new Paciente(nome, dataNascimento, codigo, cpf, email, nmrTelefone);
    }
}

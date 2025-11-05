package br.com.fiap.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CadastroPacienteDto {

    @NotBlank(message = "É necessário informar um nome!")
    @Size(max = 80)
    private String nome;

    @Past
    private LocalDate dataNascimento;

    @Size(max = 11)
    private String cpf;

    @Size(max = 80)
    private String email;

    @Size(max = 11)
    private String nmrTelefone;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNmrTelefone() {
        return nmrTelefone;
    }

    public void setNmrTelefone(String nmrTelefone) {
        this.nmrTelefone = nmrTelefone;
    }
}

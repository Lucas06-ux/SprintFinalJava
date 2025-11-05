package br.com.fiap.model;

import java.time.LocalDate;

public class Paciente extends Usuario{
    private int codigo;
    private String cpf;
    private String email;
    private String nmrTelefone;

    public Paciente() {
    }

    public Paciente(int codigo, String cpf, String email, String nmrTelefone) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.email = email;
        this.nmrTelefone = nmrTelefone;
    }

    public Paciente(String nome, LocalDate dataNascimento, int codigo, String cpf, String email, String nmrTelefone) {
        super(nome, dataNascimento);
        this.codigo = codigo;
        this.cpf = cpf;
        this.email = email;
        this.nmrTelefone = nmrTelefone;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

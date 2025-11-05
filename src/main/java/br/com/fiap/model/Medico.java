package br.com.fiap.model;

import java.time.LocalDate;

public class Medico extends Usuario{
    private int codigo;
    private String crm;
    private String especialidade;
    private double salario;

    public Medico() {
    }

    public Medico(String nome, LocalDate dataNascimento) {
        super(nome, dataNascimento);
    }

    public Medico(String nome, LocalDate dataNascimento, int codigo, String crm, String especialidade, double salario) {
        super(nome, dataNascimento);
        this.codigo = codigo;
        this.crm = crm;
        this.especialidade = especialidade;
        this.salario = salario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}

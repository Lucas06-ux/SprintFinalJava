package br.com.fiap.model;

public class Parente {
    private int codigo;
    private String nome;
    private String dsParentesco;
    private String nmrTelefone;
    private int codigoLembrete;
    private int codigoPaciente;

    public Parente(int codigo, String nome, String dsParentesco, String nmrTelefone, int codigoLembrete, int codigoPaciente) {
        this.codigo = codigo;
        this.nome = nome;
        this.dsParentesco = dsParentesco;
        this.nmrTelefone = nmrTelefone;
        this.codigoLembrete = codigoLembrete;
        this.codigoPaciente = codigoPaciente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDsParentesco() {
        return dsParentesco;
    }

    public void setDsParentesco(String dsParentesco) {
        this.dsParentesco = dsParentesco;
    }

    public String getNmrTelefone() {
        return nmrTelefone;
    }

    public void setNmrTelefone(String nmrTelefone) {
        this.nmrTelefone = nmrTelefone;
    }

    public int getCodigoLembrete() {
        return codigoLembrete;
    }

    public void setCodigoLembrete(int codigoLembrete) {
        this.codigoLembrete = codigoLembrete;
    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }
}

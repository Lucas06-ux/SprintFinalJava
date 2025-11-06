package br.com.fiap.dto.parente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AtualizarParenteDto {

    @NotBlank(message = "É necessário informar um nome")
    @Size(max = 60)
    private String nome;

    @Size(max = 30)
    private String dsParentesco;

    @Size(max = 11)
    private String nmrTelefone;

    private int codigoPaciente;

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

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }
}

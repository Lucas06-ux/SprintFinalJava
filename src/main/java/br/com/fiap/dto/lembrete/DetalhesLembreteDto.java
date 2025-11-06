package br.com.fiap.dto.lembrete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class DetalhesLembreteDto {
    private int codigo;

    @NotBlank(message = "É necessário informar uma mensagem!")
    @Size(max = 60)
    private String mensagem;

    private LocalDate dataEnvio;
    private int codigoParente;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public int getCodigoParente() {
        return codigoParente;
    }

    public void setCodigoParente(int codigoParente) {
        this.codigoParente = codigoParente;
    }
}

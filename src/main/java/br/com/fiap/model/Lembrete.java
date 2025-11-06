package br.com.fiap.model;

import java.time.LocalDate;

public class Lembrete {
    private int codigo;
    private String mensagem;
    private LocalDate dataEnvio;
    private int codigoParente;

    public Lembrete() {
    }

    public Lembrete(int codigo, String mensagem, LocalDate dataEnvio, int codigoParente) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.codigoParente = codigoParente;
    }

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

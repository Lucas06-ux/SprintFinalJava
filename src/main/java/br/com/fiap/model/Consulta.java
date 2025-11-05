package br.com.fiap.model;

import java.time.LocalDate;

public class Consulta {
    private int codigo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String link;
    private String observacoes;
    private String statusConsulta;
    private int codigoPaciente;
    private int codigoMedico;

    public Consulta(int codigo, LocalDate dataInicio, LocalDate dataFim, String link,
                    String observacoes, String statusConsulta, int codigoPaciente, int codigoMedico) {
        this.codigo = codigo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.link = link;
        this.observacoes = observacoes;
        this.statusConsulta = statusConsulta;
        this.codigoPaciente = codigoPaciente;
        this.codigoMedico = codigoMedico;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigoConsulta) {
        this.codigo = codigoConsulta;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(String statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public int getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico) {
        this.codigoMedico = codigoMedico;
    }
}

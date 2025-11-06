package br.com.fiap.dto.consulta;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AtualizarConsultaDto {
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Size(max = 30)
    private String link;

    @Size(max = 80)
    private String observacoes;

    @Size(max = 40)
    private String statusConsulta;

    private int codigoPaciente;
    private int codigoMedico;

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

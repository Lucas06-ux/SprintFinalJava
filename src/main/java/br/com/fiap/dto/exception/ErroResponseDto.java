package br.com.fiap.dto.exception;

public class ErroResponseDto {
    private String erro;
    private String detalhes;

    public ErroResponseDto(String erro, String detalhes) {
        this.erro = erro;
        this.detalhes = detalhes;
    }

    public String getErro() {
        return erro;
    }

    public String getDetalhes() {
        return detalhes;
    }
}

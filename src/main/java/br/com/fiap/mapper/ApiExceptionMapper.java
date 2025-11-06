package br.com.fiap.mapper;

import br.com.fiap.dto.exception.ErroResponseDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class ApiExceptionMapper implements ExceptionMapper<EntidadeNaoEncontrada> {

    @Override
    public Response toResponse(EntidadeNaoEncontrada exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErroResponseDto("Erro", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}

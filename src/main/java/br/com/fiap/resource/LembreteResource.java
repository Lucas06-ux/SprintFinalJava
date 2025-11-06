package br.com.fiap.resource;


import br.com.fiap.dao.LembreteDAO;
import br.com.fiap.dto.lembrete.AtualizarLembreteDto;
import br.com.fiap.dto.lembrete.CadastroLembreteDto;
import br.com.fiap.dto.lembrete.DetalhesLembreteDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Lembrete;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/lembretes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class LembreteResource {

    @Inject
    private LembreteDAO lembreteDAO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Lembrete> listar() throws SQLException {
        return lembreteDAO.listar();
    }
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        Lembrete lembrete = lembreteDAO.buscar(codigo);
        return Response.ok(mapper.map(lembrete, DetalhesLembreteDto.class)).build();
    }

    @POST
    public Response criar (@Valid CadastroLembreteDto dto, @Context UriInfo uriInfo) throws SQLException{
        Lembrete lembrete = new Lembrete();
        lembrete.setMensagem(dto.getMensagem());
        lembrete.setDataEnvio(dto.getDataEnvio());
        lembrete.setCodigoParente(dto.getCodigoParente());
        lembreteDAO.cadastrar(lembrete);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(lembrete.getCodigo()))
                .build();
        return Response.created(uri)
                .entity(lembrete)
                .build();
    }
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        lembreteDAO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar (@PathParam("id")int codigo, @Valid AtualizarLembreteDto dto) throws EntidadeNaoEncontrada, SQLException{
        Lembrete lembrete = new Lembrete();
        lembrete.setCodigo(codigo);
        lembrete.setMensagem(dto.getMensagem());
        lembrete.setDataEnvio(dto.getDataEnvio());
        lembrete.setCodigoParente(dto.getCodigoParente());
        lembreteDAO.atualizar(lembrete);
        return Response.ok().build();
    }
    @GET
    @Path("/parentes/{codigoParente}")
    public Response buscarPorPaciente(@PathParam("codigoParente") int codigoParente) throws SQLException{
        List<Lembrete> lembretes = lembreteDAO.buscarPorParente(codigoParente);
        return Response.ok(lembretes).build();
    }
}

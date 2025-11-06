package br.com.fiap.resource;


import br.com.fiap.dao.ParenteDAO;
import br.com.fiap.dto.parente.AtualizarParenteDto;
import br.com.fiap.dto.parente.CadastroParenteDto;
import br.com.fiap.dto.parente.DetalhesParenteDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Parente;
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

@Path("/parentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParenteResource {

    @Inject
    private ParenteDAO parenteDAO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Parente> listar() throws SQLException {
        return parenteDAO.listar();
    }
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        Parente parente = parenteDAO.buscar(codigo);
        return Response.ok(mapper.map(parente, DetalhesParenteDto.class)).build();
    }

    @POST
    public Response criar (@Valid CadastroParenteDto dto, @Context UriInfo uriInfo) throws SQLException{
        Parente parente = new Parente();
        parente.setNome(dto.getNome());
        parente.setDsParentesco(dto.getDsParentesco());
        parente.setNmrTelefone(dto.getNmrTelefone());
        parente.setCodigoPaciente(dto.getCodigoPaciente());
        parenteDAO.cadastrar(parente);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(parente.getCodigo()))
                .build();
        return Response.created(uri)
                .entity(parente)
                .build();
    }
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        parenteDAO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar (@PathParam("id")int codigo, @Valid AtualizarParenteDto dto) throws EntidadeNaoEncontrada, SQLException{
        Parente parente = new Parente();
        parente.setCodigo(codigo);
        parente.setNome(dto.getNome());
        parente.setDsParentesco(dto.getDsParentesco());
        parente.setNmrTelefone(dto.getNmrTelefone());
        parente.setCodigoPaciente(dto.getCodigoPaciente());
        parenteDAO.atualizar(parente);
        return Response.ok().build();
    }
    @GET
    @Path("/pacientes/{codigoPaciente}")
    public Response buscarPorPaciente(@PathParam("codigoPaciente") int codigoPaciente) throws SQLException{
        List<Parente> parentes = parenteDAO.buscarPorPaciente(codigoPaciente);
        return Response.ok(parentes).build();
    }
}

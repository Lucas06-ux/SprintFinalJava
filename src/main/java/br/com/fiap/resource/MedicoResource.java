package br.com.fiap.resource;

import br.com.fiap.dao.MedicoDAO;
import br.com.fiap.dto.medico.AtualizarMedicoDto;
import br.com.fiap.dto.medico.CadastroMedicoDto;
import br.com.fiap.dto.medico.DetalhesMedicoDto;
import br.com.fiap.dto.paciente.AtualizarPacienteDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
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

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class MedicoResource {

    @Inject
    private MedicoDAO medicoDAO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Medico> listar() throws SQLException {
        return medicoDAO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        Medico medico = medicoDAO.buscar(codigo);
        return Response.ok(mapper.map(medico, DetalhesMedicoDto.class)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        medicoDAO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarMedicoDto dto) throws EntidadeNaoEncontrada, SQLException {
        Medico medico = mapper.map(dto, Medico.class);
        medico.setCodigo(codigo);
        medicoDAO.atualizar(medico);
        return Response.ok().build();
    }

    @POST
    public Response criar (@Valid CadastroMedicoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Medico medico = mapper.map(dto, Medico.class);
        medicoDAO.cadastrar(medico);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(medico.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(medico, DetalhesMedicoDto.class)).build();
    }
}

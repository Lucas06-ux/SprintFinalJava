package br.com.fiap.resource;


import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.dto.paciente.AtualizarPacienteDto;
import br.com.fiap.dto.paciente.CadastroPacienteDto;
import br.com.fiap.dto.paciente.DetalhesPacienteDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
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

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    private PacienteDAO pacienteDAO;

    @Inject
    private ModelMapper mapper;


    @GET
    public List<Paciente> listar() throws SQLException{
        return pacienteDAO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        Paciente paciente = pacienteDAO.buscar(codigo);
        return Response.ok(mapper.map(paciente, DetalhesPacienteDto.class)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        pacienteDAO.remover(codigo);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarPacienteDto dto) throws EntidadeNaoEncontrada, SQLException {
        Paciente paciente = mapper.map(dto, Paciente.class);
        paciente.setCodigo(codigo);
        pacienteDAO.atualizar(paciente);
        return Response.ok().build();
    }


    @POST
    public Response criar (@Valid CadastroPacienteDto dto, @Context UriInfo uriInfo) throws SQLException {
        Paciente paciente = mapper.map(dto, Paciente.class);
        pacienteDAO.cadastrar(paciente);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(paciente.getCodigo())).build();

        return Response.created(uri).entity(mapper.map(paciente, DetalhesPacienteDto.class)).build();
    }
}

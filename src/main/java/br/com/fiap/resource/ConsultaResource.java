package br.com.fiap.resource;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.dto.consulta.AtualizarConsultaDto;
import br.com.fiap.dto.consulta.CadastroConsultaDto;
import br.com.fiap.dto.consulta.DetalhesConsultaDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Consulta;
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

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ConsultaResource {

    @Inject
    private ConsultaDAO consultaDAO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Consulta> listar() throws SQLException {
        return consultaDAO.listar();
    }
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        Consulta consulta = consultaDAO.buscar(codigo);
        return Response.ok(mapper.map(consulta, DetalhesConsultaDto.class)).build();
    }
    @POST
    public Response criar (@Valid CadastroConsultaDto dto, @Context UriInfo uriInfo) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setDataInicio(dto.getDataInicio());
        consulta.setDataFim(dto.getDataFim());
        consulta.setLink(dto.getLink());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setStatusConsulta(dto.getStatusConsulta());
        consulta.setCodigoPaciente(dto.getCodigoPaciente());
        consulta.setCodigoMedico(dto.getCodigoMedico());
        consultaDAO.cadastrar(consulta);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(consulta.getCodigo()))
                .build();
        return Response.created(uri)
                .entity(consulta)
                .build();
    }
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        consultaDAO.remover(codigo);
        return Response.noContent().build();
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarConsultaDto dto) throws EntidadeNaoEncontrada, SQLException {
        Consulta consulta = new Consulta();
        consulta.setCodigo(codigo);
        consulta.setDataInicio(dto.getDataInicio());
        consulta.setDataFim(dto.getDataFim());
        consulta.setLink(dto.getLink());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setStatusConsulta(dto.getStatusConsulta());
        consulta.setCodigoPaciente(dto.getCodigoPaciente());
        consulta.setCodigoMedico(dto.getCodigoMedico());
        consultaDAO.atualizar(consulta);
        return Response.ok().build();
    }

    @GET
    @Path("/pacientes/{codigoPaciente}")
    public Response buscarPorPaciente(@PathParam("codigoPaciente") int codigoPaciente) throws SQLException{
        List<Consulta> consultas = consultaDAO.buscarPorPaciente(codigoPaciente);
        return Response.ok(consultas).build();
    }
    @GET
    @Path("/medicos/{codigoMedico}")
    public Response buscarPorMedico(@PathParam("codigoMedico")int codigoMedico) throws SQLException {
        List<Consulta> consultas = consultaDAO.buscarPorMedico(codigoMedico);
        return Response.ok(consultas).build();
    }
}

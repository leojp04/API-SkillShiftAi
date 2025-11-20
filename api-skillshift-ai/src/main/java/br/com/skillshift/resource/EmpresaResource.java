package br.com.skillshift.resource;

import br.com.skillshift.bo.EmpresaBO;
import br.com.skillshift.dto.EmpresaDTO;
import br.com.skillshift.model.Empresa;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/empresas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpresaResource {

    @Inject
    EmpresaBO empresaBO;

    @GET
    public List<Empresa> listar() {
        return empresaBO.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Empresa buscarPorId(@PathParam("id") Long id) {
        return empresaBO.buscarPorId(id);
    }

    @POST
    public Response criar(EmpresaDTO dto) {
        Empresa empresa = empresaBO.criar(dto);
        return Response.created(URI.create("/empresas/" + empresa.getIdEmpresa()))
                .entity(empresa)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Empresa atualizar(@PathParam("id") Long id, EmpresaDTO dto) {
        return empresaBO.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        empresaBO.deletar(id);
        return Response.noContent().build();
    }
}

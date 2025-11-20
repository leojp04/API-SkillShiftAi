package br.com.skillshift.resource;

import br.com.skillshift.bo.CursoBO;
import br.com.skillshift.dto.CursoDTO;
import br.com.skillshift.model.Curso;
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

@Path("/cursos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CursoResource {

    @Inject
    CursoBO cursoBO;

    @GET
    public List<Curso> listar() {
        return cursoBO.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Curso buscarPorId(@PathParam("id") Long id) {
        return cursoBO.buscarPorId(id);
    }

    @POST
    public Response criar(CursoDTO dto) {
        Curso curso = cursoBO.criar(dto);
        return Response.created(URI.create("/cursos/" + curso.getIdCurso()))
                .entity(curso)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Curso atualizar(@PathParam("id") Long id, CursoDTO dto) {
        return cursoBO.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        cursoBO.deletar(id);
        return Response.noContent().build();
    }
}

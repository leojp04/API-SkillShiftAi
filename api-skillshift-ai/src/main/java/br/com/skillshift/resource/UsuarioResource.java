package br.com.skillshift.resource;

import br.com.skillshift.bo.UsuarioBO;
import br.com.skillshift.dto.UsuarioDTO;
import br.com.skillshift.model.Usuario;
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

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioBO usuarioBO;

    @GET
    public List<Usuario> listar() {
        return usuarioBO.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Usuario buscarPorId(@PathParam("id") Long id) {
        return usuarioBO.buscarPorId(id);
    }

    @POST
    public Response criar(UsuarioDTO dto) {
        Usuario usuario = usuarioBO.criar(dto);
        return Response.created(URI.create("/usuarios/" + usuario.getIdUsuario()))
                .entity(usuario)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Usuario atualizar(@PathParam("id") Long id, UsuarioDTO dto) {
        return usuarioBO.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        usuarioBO.deletar(id);
        return Response.noContent().build();
    }
}

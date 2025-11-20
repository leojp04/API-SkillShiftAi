package br.com.skillshift.resource;

import br.com.skillshift.bo.RecomendacaoBO;
import br.com.skillshift.dto.RecomendacaoDTO;
import br.com.skillshift.model.Recomendacao;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/recomendacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {

    @Inject
    RecomendacaoBO recomendacaoBO;

    @GET
    public List<Recomendacao> listar(@QueryParam("usuarioId") Long usuarioId) {
        if (usuarioId != null) {
            return recomendacaoBO.listarPorUsuario(usuarioId);
        }
        return recomendacaoBO.listarTodos();
    }

    @POST
    public Response criar(RecomendacaoDTO dto) {
        Recomendacao recomendacao = recomendacaoBO.criar(dto);
        return Response.created(URI.create("/recomendacoes/" + recomendacao.getIdRecomendacao()))
                .entity(recomendacao)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Recomendacao atualizar(@PathParam("id") Long id, RecomendacaoDTO dto) {
        return recomendacaoBO.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        recomendacaoBO.deletar(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/gerar")
    public List<Recomendacao> gerar(@QueryParam("usuarioId") Long usuarioId) {
        return recomendacaoBO.gerarRecomendacoes(usuarioId);
    }
}

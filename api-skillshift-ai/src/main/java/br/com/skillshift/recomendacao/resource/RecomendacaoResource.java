package br.com.skillshift.recomendacao.resource;

import br.com.skillshift.recomendacao.dto.RecomendacaoRequest;
import br.com.skillshift.recomendacao.dto.RecomendacaoResponse;
import br.com.skillshift.recomendacao.service.RecomendacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
import java.util.UUID;

@Path("/recomendacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {

    @Inject
    RecomendacaoService service;

    @GET
    public List<RecomendacaoResponse> listar(@QueryParam("area") String area,
                                             @QueryParam("senioridade") String senioridade) {
        return service.listar(area, senioridade);
    }

    @GET
    @Path("/{id}")
    public RecomendacaoResponse buscarPorId(@PathParam("id") UUID id) {
        return service.buscarPorId(id);
    }

    @POST
    public Response criar(@Valid RecomendacaoRequest request) {
        RecomendacaoResponse response = service.criar(request);
        return Response.created(URI.create("/recomendacoes/" + response.getId()))
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public RecomendacaoResponse atualizar(@PathParam("id") UUID id, @Valid RecomendacaoRequest request) {
        return service.atualizar(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") UUID id) {
        service.deletar(id);
        return Response.noContent().build();
    }
}

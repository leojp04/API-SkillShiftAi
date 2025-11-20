package br.com.skillshift.client;

import br.com.skillshift.dto.IAProfileRequest;
import br.com.skillshift.dto.IAProfileResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ia-client")
@Path("/cluster-profile")
public interface IAClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    IAProfileResponse gerarCluster(IAProfileRequest request);
}

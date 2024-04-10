package com.vlbgwebdav;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey="availability-api")
public interface AvailabilityServiceClient {

    @Path("/get-number-of-seats")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String getNumberOfSeats();
}

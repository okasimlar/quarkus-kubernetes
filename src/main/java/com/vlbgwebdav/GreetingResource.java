package com.vlbgwebdav;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/")
@ApplicationScoped
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    AvailablilityService availablilityService;

    @Inject
    @RestClient
    AvailabilityServiceClient availablilityServiceApi;

    @Inject
    OpenTelemetry openTelemetry;

    @ConfigProperty(name="quarkus.datasource.username")
    String username;



    @GET
    @Path(("/number-of-visitors"))
    public String getNumberOfVisitors() {
        openTelemetry.getTracerProvider().tracerBuilder("").build();
        return availablilityService.getNumberOfVisitors();
    }

    @GET
    @Path(("/number-of-seats"))
    @WithSpan("get-number-of-seats")
    public String getNumberOfSeats() {
        LOG.info("Method 'getNumberOfSeats' called.");
        return availablilityServiceApi.getNumberOfSeats();
    }

    @GET
    @Path(("/number-of-seats-wrong"))
    @WithSpan("get-number-of-seats-wrong")
    public String getNumberOfSeatsFail() {
        LOG.info("Method 'getNumberOfSeats' called.");
        return httpCall();
    }


    @GET
    @Path(("/number-of-seats-wrong2"))
    @WithSpan("get-number-of-seats-wrong2")
    public String getNumberOfSeatsFail2() {
        LOG.info("Method 'getNumberOfSeats' called.");
        return httpCall();
    }

    @GET
    @Path("/read-db-username")
    public String getDBUsername() {
        return username;
    }

    private String httpCall() {
        String output = "";
        try {

            URL url = new URL("http://vlbgwebdav-service/get-number-of-seats");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}

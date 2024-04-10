package com.vlbgwebdav;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Readiness
@Liveness
public class SampleHealthCheck implements HealthCheck {

    private static final Logger LOG = Logger.getLogger(SampleHealthCheck.class);

    @Override
    public HealthCheckResponse call() {
        //return HealthCheckResponse.down("Simple health check.");
        LOG.info("Healthcheck called.");

        return HealthCheckResponse.up("Simple health check.");
    }
}

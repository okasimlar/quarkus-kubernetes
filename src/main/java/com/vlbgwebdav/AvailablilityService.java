package com.vlbgwebdav;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvailablilityService {

    @WithSpan("availability-service")
    public String getNumberOfVisitors() {
        return "5";
    }

}

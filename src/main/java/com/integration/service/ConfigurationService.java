package com.integration.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class ConfigurationService {

    // Injecting configuration properties from application.properties or application.yaml

    @ConfigProperty(name = "dhis2.api.username")
    private String dhis2Username;

    @ConfigProperty(name = "dhis2.api.password")
    private String dhis2Password;

    @ConfigProperty(name = "dhis2.api.baseApiUrl")
    private String dhis2BaseApiUrl;

    @ConfigProperty(name = "dhis2.api.version", defaultValue = "v40_2_2")
    private String dhis2ApiVersion;

    // Optionally other configurations
    @ConfigProperty(name = "camel.route.timeout", defaultValue = "5000")
    private Integer camelRouteTimeout;

    public String getDhis2Username() {
        return dhis2Username;
    }

    public String getDhis2Password() {
        return dhis2Password;
    }

    public String getDhis2BaseApiUrl() {
        return dhis2BaseApiUrl;
    }

    public String getDhis2ApiVersion() {
        return dhis2ApiVersion;
    }

    public Integer getCamelRouteTimeout() {
        return camelRouteTimeout;
    }

    // Additional configuration methods can go here
}

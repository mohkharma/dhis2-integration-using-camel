package com.integration.bean;

import com.integration.service.ConfigurationService;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.hisp.dhis.integration.sdk.api.RemoteDhis2ClientException;

//@Singleton
@Unremovable
//@Named("dhis2ServiceBean")
@ApplicationScoped  // Ensure this class is a managed bean

public class Dhis2ServiceBean {

    @Inject
    ProducerTemplate producerTemplate;

    @Inject
    ConfigurationService configurationService; // Assuming you have a service to fetch config properties

    public Object postProgramRequest(Message message, org.hisp.dhis.api.model.v40_2_2.Program program) {
        String path = "programs";
        String apiUrl = configurationService.getDhis2BaseApiUrl();
        String username = configurationService.getDhis2Username();
        String password = configurationService.getDhis2Password();

        // Construct the endpoint URI with the dynamic parameters
        String endpointUri = String.format("dhis2://post/resource?path=%s&username=%s&password=%s&baseApiUrl=%s",
                path, username, password, apiUrl);
        Object response = null;
        try {
            response = producerTemplate.requestBody(endpointUri, program);
            message.setBody(response);  // Use shortName from incoming Program
        } catch (Exception e) {
            e.printStackTrace();

            RemoteDhis2ClientException e1 = ((RemoteDhis2ClientException) ((DefaultExchange) ((CamelExecutionException) e).getExchange()).getException().getCause());
//            e1.getHttpStatusCode();
//
//            e1.getBody()
            throw e1;
        }
        // Send request to DHIS2 and get response
        return response;
    }
}


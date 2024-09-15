package com.integration.route;

import com.integration.bean.Dhis2ServiceBean;
import com.integration.constant.HttpResponseCode;
import com.integration.dto.ApiResponse;
import com.integration.entity.Program;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.hisp.dhis.integration.sdk.api.RemoteDhis2ClientException;

import static com.integration.constant.HTTPConstant.MEDIA_TYPE_APP_JSON;

public class Dhis2ProgramRoute extends RouteBuilder {

    public static final String DIRECT_CREATE_DHIS2_PROGRAM_ROUTE = "direct:createDhis2ProgramRoute";

    @Override
    public void configure() throws Exception {
/*        org.hisp.dhis.integration.sdk.api.Dhis2Client dhis2Client = org.hisp.dhis.integration.sdk.Dhis2ClientBuilder.newClient(
                "", "", "").build();
        getCamelContext().getRegistry().bind("dhis2Client", dhis2Client);*/
        /*
        http://localhost:8080/api/dhis2/createProgram
        {
          "id": "12345",
          "name": "Test 108",
          "description": "This program focuses on health initiatives"
        }
         */

        setRestConfiguration();

        // Define the REST API
        interfaceDefintion();

        dhis2CreateProgramDefintion();
    }

    private void setRestConfiguration() {
        // REST Configuration for Quarkus
        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .contextPath("/api") // Base path for the API
                .dataFormatProperty("prettyPrint", "true");
    }

    private RestDefinition interfaceDefintion() {
        return rest("/dhis2")
                .bindingMode(RestBindingMode.json)
                .post("/createProgram")
                .type(Program.class) // Expect Program JSON in the request body
                .outType(ApiResponse.class)
                .consumes(MEDIA_TYPE_APP_JSON)
                .produces(MEDIA_TYPE_APP_JSON)
                .to(DIRECT_CREATE_DHIS2_PROGRAM_ROUTE);  // Use 'direct:' to invoke the route
    }

    private void dhis2CreateProgramDefintion() {
        from(DIRECT_CREATE_DHIS2_PROGRAM_ROUTE)
                .doTry()
                .log("Received DHIS2 Program creation request: ${body}")
                .bean("dhis2InputHandlerBean","postProgramBody")
                .log("Sending JSON to DHIS2 API: ${body}")
                .bean(Dhis2ServiceBean.class, "postProgramRequest")  // Call the bean method
//                .to("dhis2://post/resource?path=programs" +
//                        "&username={{dhis2.api.username}}&password={{dhis2.api.password}}&baseApiUrl={{dhis2.api.baseApiUrl}}")
                .unmarshal().json(org.hisp.dhis.api.model.v40_2_2.WebMessage.class)
                .log("Response from DHIS2 API: ${body}")
                .log("Response headers from DHIS2 API: ${headers}")
                .log("Exchange properties: ${exchange.properties}")
                .choice()
                .when(exchange -> {
                    org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
                            exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
                    return HttpResponseCode.CREATED != webMessage.getHttpStatusCode().get();
                })
                .log(LoggingLevel.ERROR, "Error from DHIS2: Status ${body.httpStatusCode}, Message: ${body.message}")
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, exchange -> {
//                    org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
//                            exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
//                    return webMessage.getHttpStatusCode().orElse(HttpResponseCode.INTERNAL_SERVER_ERROR);
//                })
                .setBody(exchange -> {
                    org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
                            exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setHttpStatusCode(webMessage.getHttpStatusCode().get());
                    apiResponse.setMessage(webMessage.getResponse().get().getMessage().orElse("Unknown error occurred"));
                    apiResponse.setResponse(webMessage.getResponse().get().toString());
                    return apiResponse;
                })
                .otherwise()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpResponseCode.CREATED))
//                .setBody(simple("{ \"message\": \"Program created successfully\" }"))
                .setBody(
                        exchange -> {
                            org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
                                    exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
                            ApiResponse apiResponse = new ApiResponse();
                            apiResponse.setHttpStatusCode(webMessage.getHttpStatusCode().get());
                            apiResponse.setMessage(webMessage.getResponse().get().getMessage().orElse("Program created successfully"));
                            apiResponse.setResponse(webMessage.getResponse().get().toString());
                            return apiResponse;
                        }
                )
                .end()
                .endDoTry()
//                .doCatch(RemoteDhis2ClientException.class)
//                .process(exchange -> {
//                    // Extract exception details
//                    RemoteDhis2ClientException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, RemoteDhis2ClientException.class);
//
//                    // Log the exception details
//                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, exception.getHttpStatusCode()); // Set HTTP status 500 for internal server error
//
//                    ApiResponse apiResponse = new ApiResponse();
//                    apiResponse.setHttpStatusCode(exception.getHttpStatusCode());
//                    apiResponse.setMessage(exception.getMessage());
//                    apiResponse.setResponse(exception.getBody());
//                    // Optionally set a more detailed error message if needed
//                    exchange.getMessage().setBody(apiResponse);
//                })
                .doCatch(Exception.class)
//                .log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
                .process(exchange -> {
                    // Extract exception details
                    Exception exception1 = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

                    if (exception1 instanceof RemoteDhis2ClientException){
                        RemoteDhis2ClientException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, RemoteDhis2ClientException.class);

                        // Log the exception details
                        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, exception.getHttpStatusCode()); // Set HTTP status 500 for internal server error

                        ApiResponse apiResponse = new ApiResponse();
                        apiResponse.setHttpStatusCode(exception.getHttpStatusCode());
                        apiResponse.setMessage(exception.getMessage());
                        apiResponse.setResponse(exception.getBody());
                        // Optionally set a more detailed error message if needed
                        exchange.getMessage().setBody(apiResponse);
                    }else {
                        // Log the exception details
                        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpResponseCode.INTERNAL_SERVER_ERROR); // Set HTTP status 500 for internal server error

                        ApiResponse apiResponse = new ApiResponse();
                        apiResponse.setHttpStatusCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
                        apiResponse.setMessage("Failure in DHIS2 API execution.");
                        // Optionally set a more detailed error message if needed
                        exchange.getMessage().setBody(apiResponse);
                    }
                })
                .marshal().json()
                .end()
        ;
    }

}
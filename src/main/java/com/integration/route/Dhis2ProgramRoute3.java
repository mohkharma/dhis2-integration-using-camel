//package com.integration.route;
//
//import com.integration.constant.HttpResponseCode;
//import com.integration.dto.ApiResponse;
//import com.integration.entity.Program;
//import org.apache.camel.Exchange;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//import org.apache.camel.model.rest.RestDefinition;
//
//import static com.integration.constant.HTTPConstant.MEDIA_TYPE_APP_JSON;
//
//public class Dhis2ProgramRoute3 extends RouteBuilder {
//
//    public static final String DIRECT_CREATE_DHIS2_PROGRAM_ROUTE = "direct:createDhis2ProgramRoute3";
//
//    @Override
//    public void configure() throws Exception {
///*        org.hisp.dhis.integration.sdk.api.Dhis2Client dhis2Client = org.hisp.dhis.integration.sdk.Dhis2ClientBuilder.newClient(
//                "", "", "").build();
//        getCamelContext().getRegistry().bind("dhis2Client", dhis2Client);*/
//        /*
//        http://localhost:8080/api/dhis2/createProgram
//        {
//          "id": "12345",
//          "name": "Test 108",
//          "description": "This program focuses on health initiatives"
//        }
//         */
//
//        setRestConfiguration();
//
//        // Define the REST API
//        interfaceDefintion();
//
//        dhis2CreateProgramDefintion();
//    }
//
//    private void setRestConfiguration() {
//        // REST Configuration for Quarkus
//        restConfiguration()
//                .bindingMode(RestBindingMode.json)
//                .contextPath("/api") // Base path for the API
//                .dataFormatProperty("prettyPrint", "true");
//    }
//
//    private RestDefinition interfaceDefintion() {
//        return rest("/dhis3")
//                .bindingMode(RestBindingMode.json)
//                .post("/createProgram")
//                .type(Program.class) // Expect Program JSON in the request body
//                .outType(ApiResponse.class)
//                .consumes(MEDIA_TYPE_APP_JSON)
//                .produces(MEDIA_TYPE_APP_JSON)
//                .to(DIRECT_CREATE_DHIS2_PROGRAM_ROUTE);  // Use 'direct:' to invoke the route
//    }
//
//    private void dhis2CreateProgramDefintion() {
//        from(DIRECT_CREATE_DHIS2_PROGRAM_ROUTE)
//                .doTry()
//                .log("Received DHIS2 Program creation request: ${body}")
//                .bean("dhis2InputHandlerBean","postProgramBody")
////                .process(new Processor() {
////                    @Override
////                    public void process(Exchange exchange) throws Exception {
////                        Program program = exchange.getIn().getBody(Program.class);
////                        exchange.getIn().setBody(new org.hisp.dhis.api.model.v40_2_2.Program()
////                                .withName(program.getName())  // Set withName using value from incoming JSON
////                                .withProgramType(org.hisp.dhis.api.model.v40_2_2.Program.ProgramTypeRef.WITHOUT_REGISTRATION)  // Set ProgramType
////                                .withShortName(program.getName()));  // Use shortName from incoming Program
////                    }
////                })
//                .log("Sending JSON to DHIS2 API: ${body}")
//                .to("dhis2://post/resource?path=programs" +
//                        "&username={{dhis2.api.username}}&password={{dhis2.api.password}}&baseApiUrl={{dhis2.api.baseApiUrl}}")
//                .unmarshal().json(org.hisp.dhis.api.model.v40_2_2.WebMessage.class)
//                .log("Response from DHIS2 API: ${body}")
//                .log("Response headers from DHIS2 API: ${headers}")
//                .log("Exchange properties: ${exchange.properties}")
//                .choice()
//                .when(exchange -> {
//                    org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
//                            exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
//                    return HttpResponseCode.CREATED != webMessage.getHttpStatusCode().get();
//                })
//                .otherwise()
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpResponseCode.CREATED))
////                .setBody(simple("{ \"message\": \"Program created successfully\" }"))
//                .setBody(
//                        exchange -> {
//                            org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
//                                    exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
//                            ApiResponse apiResponse = new ApiResponse();
//                            apiResponse.setHttpStatusCode(webMessage.getHttpStatusCode().get());
//                            apiResponse.setMessage(webMessage.getResponse().get().getMessage().orElse("Program created successfully"));
//                            apiResponse.setResponse(webMessage.getResponse().get().toString());
//                            return apiResponse;
//                        }
//                )
//                .end()
//                .endDoTry()
//                .doCatch(Exception.class)
////                .log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
//                .process(exchange -> {
//                    // Extract exception details
//                    Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
//
//                    // Log the exception details
//                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpResponseCode.BAD_REQUEST); // Set HTTP status 500 for internal server error
//
//                    ApiResponse apiResponse = new ApiResponse();
//                    apiResponse.setHttpStatusCode(HttpResponseCode.BAD_REQUEST);
//                    apiResponse.setMessage("Failure in DHIS2 API execution.");
//                    // Optionally set a more detailed error message if needed
//                    exchange.getMessage().setBody(apiResponse);
//                })
//                .marshal().json()
//                .end()
//        ;
//    }
//
//}
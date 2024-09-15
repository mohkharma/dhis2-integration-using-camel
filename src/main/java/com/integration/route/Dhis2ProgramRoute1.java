//package com.integration.route;
//
//import com.integration.constant.HttpResponseCode;
//import com.integration.entity.Program;
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//
//import static com.integration.constant.HTTPConstant.MEDIA_TYPE_APP_JSON;
//
//public class Dhis2ProgramRoute1 extends RouteBuilder {
//
//    @Override
//    public void configure() throws Exception {
////        org.hisp.dhis.integration.sdk.api.Dhis2Client dhis2Client = org.hisp.dhis.integration.sdk.Dhis2ClientBuilder.newClient(
////                "", "", "").build();
////        getCamelContext().getRegistry().bind("dhis2Client", dhis2Client);
//        /*
//        http://localhost:8080/api/dhis2/createProgram
//        {
//          "id": "12345",
//          "name": "Test 108",
//          "description": "This program focuses on health initiatives"
//        }
//
//         */
//        // REST Configuration for Quarkus
//        restConfiguration()
//                .bindingMode(RestBindingMode.json)
//                .contextPath("/api") // Base path for the API
//                .dataFormatProperty("prettyPrint", "true");
//
//        // Define the REST API
//        rest("/dhis2v2")
//                .bindingMode(RestBindingMode.json)
//                .post("/createProgram")
//                .type(Program.class) // Expect Program JSON in the request body
//                .consumes(MEDIA_TYPE_APP_JSON)
//                .produces(MEDIA_TYPE_APP_JSON)
//
//                .to("direct:createProgramRoute");  // Use 'direct:' to invoke the route
//
//        from("direct:createProgramRoutev2")
//                .doTry()
//                .log("Received DHIS2 Program creation request: ${body}")
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        Program program = exchange.getIn().getBody(Program.class);
//                        exchange.getIn().setBody(new org.hisp.dhis.api.model.v40_2_2.Program()
//                                .withName(program.getName())  // Set withName using value from incoming JSON
//                                .withProgramType(org.hisp.dhis.api.model.v40_2_2.Program.ProgramTypeRef.WITHOUT_REGISTRATION)  // Set ProgramType
//                                .withShortName(program.getName()));  // Use shortName from incoming Program
//                    }
//                })
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
//                    return HttpResponseCode.CREATED != webMessage.getHttpStatusCode().orElse(500);
//                })
//                .otherwise()
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpResponseCode.CREATED))
//                .setBody(simple("{ \"message\": \"Program created successfully\" }"))
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
//                    // Optionally set a more detailed error message if needed
//                    exchange.getIn().setBody(String.format("{\"error\": \"Internal server error occurred\"}"/*,
//                            exception != null ? exception.getMessage() : "Unknown error"*/));
//
//
////                    org.hisp.dhis.api.model.v40_2_2.WebMessage webMessage =
////                            exchange.getMessage().getBody(org.hisp.dhis.api.model.v40_2_2.WebMessage.class);
////                    // Set the DHIS2 error HTTP status code
////                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE,
////                            webMessage.getHttpStatusCode().orElse(HttpResponseCode.CLIENT_ERROR));
////                    // Set the DHIS2 error message in the response body
////                    exchange.getMessage().setBody(String.format("{\"error\": \"Internal server error occurred: %s\"}",
////                            webMessage.getMessage() != null ? webMessage.getMessage() : "Unknown error"));
//                })
////                .log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
////                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpResponseCode.INTERNAL_SERVER_ERROR))  // Set HTTP status 500 for internal server error
////                .setBody(simple("{ \"error\": \"Internal server error occurred\" }"))
////                .setBody(simple("{ \"error\": \"Internal server error occurred\" }"))
//                .end()
//        ;
//
//    }
////    public void configureqq() throws Exception {
////
////        rest("/contact")
////                .bindingMode(RestBindingMode.json)
////                .post()
////                .type(Program.class)
////                .outType(Program.class)
////                .consumes(MEDIA_TYPE_APP_JSON)
////                .produces(MEDIA_TYPE_APP_JSON)
////                .route()
////                .routeId("save-contact-route")
////                .log("saving contacts")
////                .to("jpa:" + Contact.class.getName())
////                .endRest()
////                .get()
////                .outType(List.class)
////                .produces(MEDIA_TYPE_APP_JSON)
////                .route()
////                .routeId("list-contact-route")
////                .log("listing contacts")
////                .to("jpa:" + Contact.class.getName()+ "?query={{query}}")
////                .endRest();
////
////    }
//
//}
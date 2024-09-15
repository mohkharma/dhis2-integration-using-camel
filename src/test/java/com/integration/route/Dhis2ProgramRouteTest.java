package com.integration.route;

import com.integration.entity.Program;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.apache.camel.component.mock.MockEndpoint.assertIsSatisfied;

public class Dhis2ProgramRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new Dhis2ProgramRoute();
    }

    @Test
    public void testCreateDHIS2ProgramRoute() throws Exception {
        // Prepare a sample Program
        Program program = new Program("1", "Sample Program", "Description");

        // Send the sample Program to the direct endpoint
        getMockEndpoint("mock:result").expectedMessageCount(1);
        getMockEndpoint("mock:result").expectedBodiesReceived(program);

        template.sendBody("direct:createProgramRoute", program);

        // Assert that the expected endpoint received the message
        assertIsSatisfied(context);
    }

    @Test
    public void testCreateProgramRoute() throws Exception {
        // Prepare a sample Program
        Program program = new Program("1", "Sample Program", "Description");

        // Set up the mock endpoint
        MockEndpoint mockEndpoint = getMockEndpoint("mock:dhis2-api");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(program);

        // Send the sample Program to the direct endpoint
        template.sendBody("direct:createProgramRoute", program);

        // Assert that the expected endpoint received the message
        assertIsSatisfied(context);

        // Additional assertions or verifications if needed
    }
}

package com.integration.bean;

import com.integration.entity.Program;
import io.quarkus.arc.Unremovable;
//import io.quarkus.oidc.client.OidcClient;
//import io.quarkus.oidc.client.Tokens;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.camel.Message;

//import javax.annotation.PostConstruct;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.inject.Singleton;

@Singleton
@Unremovable
@Named("dhis2InputHandlerBean")
public class Dhis2InputHandlerBean {

//    @Inject
//    OidcClient client;
//
//    volatile Tokens currentTokens;
//
//    @PostConstruct
//    public void init() {
//        currentTokens = client.getTokens().await().indefinitely();
//    }

    public void postProgramBody(Message message){

        Program program = message.getBody(Program.class);
        message.setBody(new org.hisp.dhis.api.model.v40_2_2.Program()
                .withName(program.getName())  // Set withName using value from incoming JSON
                .withProgramType(org.hisp.dhis.api.model.v40_2_2.Program.ProgramTypeRef.WITHOUT_REGISTRATION)  // Set ProgramType
                .withShortName(program.getName()));  // Use shortName from incoming Program

//        message.setHeader("Authorization", "Bearer " + tokens.getAccessToken() );
    }
}

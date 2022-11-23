package com.devmatthias.auth;

import com.devmatthias.config.ApplicationProperties;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;

import java.io.IOException;

public class AuthenticationProvider {

    public AuthenticationProvider(){ }

    public IGraphServiceClient getAuthProvider() throws IOException {
        // using com.microsoft.graph.auth
        ClientCredentialProvider authProvider = new ClientCredentialProvider(
                ApplicationProperties.getClientId(),
                ApplicationProperties.getScopeList(),
                ApplicationProperties.getClientSecret(),
                ApplicationProperties.getTenantId(),
                NationalCloud.Global);

        return GraphServiceClient
                .builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }
}

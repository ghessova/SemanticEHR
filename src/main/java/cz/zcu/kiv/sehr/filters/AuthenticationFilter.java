package cz.zcu.kiv.sehr.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import cz.zcu.kiv.sehr.bindings.Secured;
import cz.zcu.kiv.sehr.services.AuthenticationService;

@Secured
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // TODO For some (FUCK JAVA!!!!) reason, this doesn't trigger

        String token = requestContext.getHeaderString("token");

        if (token == null || !AuthenticationService.getInstance().validateToken(token))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}

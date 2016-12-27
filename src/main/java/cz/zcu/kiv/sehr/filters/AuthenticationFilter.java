package cz.zcu.kiv.sehr.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import cz.zcu.kiv.sehr.services.AuthenticationService;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // TODO Figure out why this doesn't work with NameBinding
        if (requestContext.getUriInfo().getPath().endsWith("swagger.json") || requestContext.getUriInfo().getPath().contains("/auth/"))
            return;

        String token = requestContext.getHeaderString("token");

        if (token == null || !AuthenticationService.getInstance().validateToken(token))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}

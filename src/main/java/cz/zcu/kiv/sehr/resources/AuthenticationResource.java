package cz.zcu.kiv.sehr.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.zcu.kiv.sehr.model.AuthenticationResponse;
import cz.zcu.kiv.sehr.model.UserWrapper;
import cz.zcu.kiv.sehr.services.AuthenticationService;
import cz.zcu.kiv.sehr.model.AuthenticationRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource maintaining user authentication
 *
 */
@Path("auth")
@Api(value="login", description="Maintaing authentication")
public class AuthenticationResource {

    /**
     * Check if presented token exists
     *
     * @return JSON response
     */
    @GET
    @Path("login")
    @ApiOperation(value="Check if user is logged in")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Token is valid"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response isLogged(@HeaderParam("token") String apiToken) {
        boolean result = AuthenticationService.getInstance().validateToken(apiToken);

        return Response.status((result == true) ? Response.Status.NO_CONTENT : Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Check user credentials and login user
     *
     * @return JSON response
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Authenticate user", response = AuthenticationResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 403, message = "Username or password") } )
    public Response loginUser(AuthenticationRequest credentials) {
        Response res = null;
        AuthenticationResponse response = new AuthenticationResponse();

        UserWrapper user = null;
        String token = null;

        try {
            user = AuthenticationService.getInstance().authenticate(credentials.getUsername(), credentials.getPassword());
            token = AuthenticationService.getInstance().issueToken(user);

            response.setUser(user);
            response.setAccessToken(token);

            res = Response.status(Response.Status.OK)
                    .entity(response)
                    .build();
        } catch (Exception e) {
            res = Response.status(Response.Status.FORBIDDEN).build();
        }

        return res;
    }

    /**
     * Logout user from API, invalidate token
     *
     * @return JSON response
     */
    @POST
    @Path("logout")
    @ApiOperation(value="Invalidate user's token")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Token is valid"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response destroyToken(@HeaderParam("token") String apiToken) {
        boolean result = AuthenticationService.getInstance().invalidateToken(apiToken);

        return Response.status((result == true) ? Response.Status.NO_CONTENT : Response.Status.UNAUTHORIZED).build();
    }
}

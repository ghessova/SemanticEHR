package cz.zcu.kiv.sehr.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import cz.zcu.kiv.sehr.model.AuthenticationResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Resource maintaing user authentication
 *
 */
@Path("")
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
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Token is valid"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response isLogged(@HeaderParam("token") String apiToken) {

        // TODO Write logic of checking token validity
        return Response.status(200)
            .entity(new Document("logged", true).toJson())
            .build();
    }

    /**
     * Check user credentials and login user
     *
     * @return JSON response
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Authenticate user", response = AuthenticationResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 403, message = "Username or password") } )
    public Response loginUser(@FormParam("user") String user, @FormParam("password") String password) {

        // TODO Write logic of actuall user creating
        return Response.status(200)
            .entity(new Document("logged", true).toJson())
            .build();
    }

    /**
     * Logout user from API, invalidate token
     *
     * @return JSON response
     */
    @POST
    @Path("logout")
    @ApiOperation(value="Invalidate user's token")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Token is valid"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response destroyToken(@HeaderParam("token") String apiToken) {

        // TODO Write logic of destroying token
        return Response.status(204)
            .build();
    }
}

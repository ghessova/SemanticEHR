package cz.zcu.kiv.sehr.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

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
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Check if user is logged in", response = Document.class)
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
    @ApiOperation(value="Authenticate user", response = Document.class)
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
    public Response destroyToken(@HeaderParam("token") String apiToken) {

        // TODO Write logic of destroying token
        return Response.status(204)
            .build();
    }
}

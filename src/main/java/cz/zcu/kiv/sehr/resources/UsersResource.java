package cz.zcu.kiv.sehr.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataParam;

import cz.zcu.kiv.sehr.model.UserWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Users resource (exposed at "users" path)
 *
 */
@Path("users")
@Api(value="users", description="Service for managing users")
public class UsersResource {

    /**
     * Method handling HTTP GET requests and return list of users.
     *
     * @return All registered users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Return all registered users", response = UserWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response getUsers() {

        // TODO Actuall user getting
        return Response.status(200)
            .entity(new Document().toJson())
            .build();
    }

    /**
     * Method handling HTTP POST requests and add new user.
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Create new user", response = UserWrapper.class )
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response addUser(@FormDataParam("user") UserWrapper user) {

        // TODO Actual adding user logic
        return Response.status(204)
            .build();
    }

    /**
     * Method handling HTTP GET requests and get user according to id.
     *
     * @return Serialized user with requested id
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Get user with requested ID", response = UserWrapper.class)
    @ApiResponses(value = { @ApiResponse(code = 403, message = "Invalid access token") })
    public Response getUser(@PathParam("userId") String documentId) {

        // TODO Actual user finding
        return Response.status(200)
            .entity(new Document().toJson())
            .build();
    }

    /**
    * Method handling HTTP PUT requests and update user according to id.
    */
    @PUT
    @Path("{userId}")
    @ApiOperation(value="Update user of requested ID")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response updateUser(@PathParam("userId") String documentId, @FormDataParam("user") UserWrapper user) {

        // TODO Write actuall updating logic
        return Response.status(204)
            .build();
    }


    /**
     * Method handling HTTP DELETE requests and delete user according to id.
     *
     */
    @DELETE
    @Path("{userId}")
    @ApiOperation(value="Delete user of requested ID")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response deleteUser(@PathParam("userId") String documentId) {

        // TODO Write actuall updating logic
        return Response.status(204)
            .build();
    }
}

package cz.zcu.kiv.sehr.resources;

import io.swagger.annotations.Api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

/**
 * Users resource (exposed at "users" path)
 *
 */
@Path("users")
@Api(description="Service for managing users")
public class UsersResource {

    /**
     * Method handling HTTP GET requests and return list of users.
     *
     * @return All registered users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response addUser() {

        // TODO Actual adding user logic
        return Response.status(204)
            .build();
    }

    /**
     * Method handling HTTP @PUT requests and update users.
     *
     */
    @PUT
    public Response updateUsers() {

        // TODO Write actuall updating logic
        return Response.status(204)
            .build();
    }


    /**
     * Method handling HTTP GET requests and get user according to id.
     *
     * @return Serialized user with requested id
     */
    @GET
    @Path("id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {

        // TODO Actual user finding
        return Response.status(200)
            .entity(new Document().toJson())
            .build();
    }

    /**
    * Method handling HTTP PUT requests and update user according to id.
    */
    @PUT
    @Path("id")
    public Response updateUser() {

        // TODO Write actuall updating logic
        return Response.status(204)
            .build();
    }


    /**
     * Method handling HTTP DELETE requests and delete user according to id.
     *
     */
    @DELETE
    @Path("id")
    public Response deleteUser() {

        // TODO Write actuall updating logic
        return Response.status(204)
            .build();
    }
}

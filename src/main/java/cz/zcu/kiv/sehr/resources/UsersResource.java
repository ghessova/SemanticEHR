package cz.zcu.kiv.sehr.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.mongodb.util.JSON;

import cz.zcu.kiv.sehr.dao.UsersDAO;
import cz.zcu.kiv.sehr.model.UserWrapper;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.utils.Utils;

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

    /** Default page size */
    private static final int PAGE_SIZE = 10;

    /** Access to DB */
    private static final UsersDAO UsersDB = UsersDAO.getInstance();

    /**
     * Method handling HTTP GET requests and return list of users.
     *
     * @return All registered users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Return all registered users", response = UserWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response getUsers(@HeaderParam("token") String token, @QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + PAGE_SIZE) String count) {
        Response res = null;

        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null)
            res = Response.status(Response.Status.BAD_REQUEST).build();

        // Build response using obtained user data
        res = Response.status(Response.Status.OK)
                .entity(JSON.serialize(UsersDB.getUsers(pagingParams)))
                .build();

        return res;
    }

    /**
     * Method handling HTTP POST requests and add new user.
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Create new user with requested data")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response addUser(@HeaderParam("token") String token, UserWrapper user) {
        Response res = null;
        long added = UsersDB.insertUser(user);

        // Build response
        res = (added > 0) ? Response.status(Response.Status.NO_CONTENT).build() :
            Response.status(Response.Status.BAD_REQUEST).build();

        return res;
    }

    /**
     * Method handling HTTP GET requests and get user according to id.
     *
     * @return Serialized user with requested id
     */
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Get user with requested ID", response = UserWrapper.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "User not found"), @ApiResponse(code = 401, message = "Invalid access token") })
    public Response getUser(@HeaderParam("token") String token, @PathParam("username") String username) {
        Response res = null;
        Document user = UsersDB.findUserByUsername(username);

        // According to result, build response
        res = (user == null) ? Response.status(Response.Status.NOT_FOUND).build() :
            Response.status(Response.Status.OK).entity(user.toJson()).build();

        return res;
    }

    /**
    * Method handling HTTP PUT requests and update user according to id.
    */
    @PUT
    @ApiOperation(value="Update user with presented data")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 400, message= "Bar parameters"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response updateUser(@HeaderParam("token") String token, UserWrapper user) {
        Response res = null;
        long updated = UsersDB.insertUser(user);

        // Build response
        res = (updated > 0) ? Response.status(Response.Status.NO_CONTENT).build() :
            Response.status(Response.Status.BAD_REQUEST).build();

        return res;
    }


    /**
     * Method handling HTTP DELETE requests and delete user according to id.
     *
     */
    @DELETE
    @Path("{username}")
    @ApiOperation(value="Delete user of requested ID")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 404, message= "User not found"), @ApiResponse(code = 401, message = "Invalid access token") } )
    public Response deleteUser(@HeaderParam("token") String token, @PathParam("username") String username) {
        Response res = null;
        long deleted = UsersDB.deleteUserByName(username);

        // Build response
        res = (deleted > 0) ? Response.status(Response.Status.NO_CONTENT).build() :
            Response.status(Response.Status.NOT_FOUND).build();

        return res;
    }
}

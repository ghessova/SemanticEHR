package cz.zcu.kiv.sehr.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cz.zcu.kiv.sehr.dao.UserDao;
import cz.zcu.kiv.sehr.dao.UserDao.ROLE;
import io.swagger.annotations.Api;

/**
 * Users resource (exposed at "users" path)
 * Specification https://loopback.io/doc/en/lb2/User-REST-API.html
 */
@Path("users")
@Api
public class UsersResource {
	
	/**
     * Method handling HTTP GET requests and return list of users.
     *
     * @return List<User> that will be returned as a ....
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<UserDao> getUsers() {
    	
    	// some fake data
    	List<UserDao> users = new ArrayList<UserDao>();
    	users.add(new UserDao("user_a", "a", "a@a.cz", ROLE.ADMIN));
    	users.add(new UserDao("user_b", "b", "b@b.cz", ROLE.USER));
    	users.add(new UserDao("user_c", "c", "c@c.cz", ROLE.USER));
    	
    	return users;
    	
    }
    
	/**
     * Method handling HTTP POST requests and add new user.
     */
    @POST
    public void addUser() {
    	// some fake data - will be set according to form content
    	List<UserDao> users = new ArrayList<UserDao>();
    	users.add(new UserDao("user_a", "a", "a@a.cz", ROLE.ADMIN));
    }
    
    /**
     * Method handling HTTP @PUT requests and update users.
     */
    @PUT
    public void updateUsers() {
        // update users - will be set according to form content
    }
    
    
	/**
     * Method handling HTTP GET requests and get user according to id.
     *
     * @return User that will be returned as a ...
     */
    @GET
    @Path("id")
    @Produces(MediaType.TEXT_PLAIN)
    public UserDao getUser() {
    	// return some fake data
        return new UserDao("user_a", "a", "a@a.cz", ROLE.ADMIN);
    }

	/**
    * Method handling HTTP PUT requests and update user according to id.
    */
    @PUT
    @Path("id")
    public void updateUser() {
    	// update user - will be set according to form content
    }
    
	/**
     * Method handling HTTP DELETE requests and delete user according to id.
     */
    @DELETE
    @Path("id")
    public void getItaaa() {
    	// delete user
    }
}

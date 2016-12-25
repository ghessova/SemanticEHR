package cz.zcu.kiv.sehr.resources;

import com.mongodb.util.JSON;
import cz.zcu.kiv.sehr.archetypes.ArchetypeParser;
import cz.zcu.kiv.sehr.dao.ArchetypesDAO;
import cz.zcu.kiv.sehr.model.ArchetypeRequest;
import cz.zcu.kiv.sehr.utils.Config;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

/**
 * Created by ghessova on 17.12.16.
 */
@Path("archetypes")
@Api(value="archetypes", description="Service for managing archetypes.")
public class ArchetypesResource {

    /** The path to the folder where we want to store the uploaded files */
    private static final String UPLOAD_FOLDER = "uploaded/";
    private ArchetypesDAO archetypesDAO = ArchetypesDAO.getInstance();

    public static final int DEFAULT_LIMIT = 10;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds archetypes", response = Document.class)
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response getArchetypes(@QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + DEFAULT_LIMIT) String count) {

            PagingParams pagingParams = Utils.processPagingParams(from, count);
            if (pagingParams == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            List<Document> results = archetypesDAO.getArchetypes(pagingParams);

            return Response
                    .status(Response.Status.OK)
                    .entity(JSON.serialize(results))
                    .build();

    }

    @GET
    @Secured
    @Path("{archetypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds archetype with specific ID", response = Document.class)
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response getArchetype(@PathParam("archetypeId") String archetypeId) {
        Document result =  archetypesDAO.findArchetypeById(archetypeId);
        if (result == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(result.toJson()).build();
        }

    }

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    @ApiOperation(value="Lists all archetypes", response = Document.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Invalid access token") })
    public Response listArchetypes(@QueryParam("from") String from, @QueryParam("count") String count) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @DELETE
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Deletes archetype with specific ID")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response deleteArchetype(@QueryParam("archetypeId") String archetypeId) {
        long deleted = archetypesDAO.deleteArchetypeById(archetypeId);

        if (deleted > 0) {
            return Response.status(200).build();
        }
        else {
            return Response.status(404).build();
        }


    }

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Lists all archetype addition requests", response = ArchetypeRequest.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response listRequests(@QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + DEFAULT_LIMIT) String count) {
        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Document> results = archetypesDAO.getRequests(pagingParams); // TODO filter by user
        return Response
                .status(Response.Status.OK)
                .entity(JSON.serialize(results))
                .build();
    }

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Request for adding new archetype")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response requestAddingArchetype(JsonObject body, @QueryParam("archetypeId") String archetypeId) {
        long added = archetypesDAO.insertRequest("0", archetypeId); // TODO add real user id
        if(added > 0)
            return Response.status(200).build();
        else
            return Response.status(400).build();
        // TODO create unique index on userId and archetypeId
    }

    @DELETE
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Delete request for archetype addition")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response deleteRequest(@QueryParam("archetypeId") String archetypeId) {
        long deleted = archetypesDAO.deleteRequestById(archetypeId);

        if (deleted > 0) {
            return Response.status(200).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @POST
    @Secured
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value="Add archetype from ADL file")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response uploadArchetype(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
        try {
            ArchetypeParser archetypeParser = new ArchetypeParser();
            Document document = archetypeParser.processArchetypeInputStream(uploadedInputStream);
            int code = archetypesDAO.insertArchetype(document);
            if (code == 1) {
                return Response.status(200).build();
            }
            else {
                return Response.status(400).build();
            }

        } catch (Exception e) {
            return Response.status(500).build();
        }

    }

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    @ApiOperation(value="Searches for archetypes by keyword", response = Document.class)
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response searchArchetypes(@QueryParam("keyword") @DefaultValue("0") String keyword, @QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + DEFAULT_LIMIT) String count) {

        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<Document> results = archetypesDAO.searchForArchetypes(keyword, pagingParams);

        return Response
                .status(Response.Status.OK)
                .entity(JSON.serialize(results))
                .build();

    }

    /**
     * Utility method to save InputStream data to target location/file
     *
     * @param inStream
     *            - InputStream to be saved
     * @param target
     *            - full path to destination file
     */
    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }
    /**
     * Creates a folder to desired location if it not already exists
     *
     * @param dirName
     *            - full path to the folder
     * @throws SecurityException
     *             - in case you don't have permission to create the folder
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }



}

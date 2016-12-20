package cz.zcu.kiv.sehr.resources;

import cz.zcu.kiv.sehr.archetypes.ArchetypeParser;
import cz.zcu.kiv.sehr.database.MongoDBConnector;
import cz.zcu.kiv.sehr.utils.Config;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.openehr.am.archetype.Archetype;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by ghessova on 17.12.16.
 */
@Path("archetypes")
@Api(value="archetypes", description="Service for managing archetypes.")
public class ArchetypesResource {

    /** The path to the folder where we want to store the uploaded files */
    private static final String UPLOAD_FOLDER = "uploaded/";


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds archetype with specific ID", response = Document.class)
    public Response getArchetype(@HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    @ApiOperation(value="Lists all archetypes", response = Document.class, responseContainer = "List")
    public Response listArchetypes(@HeaderParam("from") String from, @HeaderParam("count") String count) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Deletes archetype with specific ID")
    public Response deleteArchetype(@HeaderParam("archetypeId") String archetypeId) {
        archetypeId = "5858fcf7adb83d12ce02bacb";
        long deleted =  Config.getDBC().removeDocumentById(archetypeId, "definitions");

        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Lists all archetype addition requests", response = Document.class, responseContainer = "List")
    public Response listRequests(@HeaderParam("from") String from, @HeaderParam("size") String size, @HeaderParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Request for adding new archetype")
    public Response requestAddingArchetype(JsonObject body, @HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    @ApiOperation(value="Delete request for archetype addition")
    public Response deleteRequest(@HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Add archetype from ADL file")
    public Response uploadArchetype(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
        try {
            ArchetypeParser archetypeParser = new ArchetypeParser();
            Document document = archetypeParser.processArchetypeInputStream(uploadedInputStream);
            Config.getDBC().addDocument(document, Config.DEFINITIONS);
            return Response.status(200).entity("Success").build();

        } catch (Exception e) {
            return Response.status(500).entity("Failed").build();
        }

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

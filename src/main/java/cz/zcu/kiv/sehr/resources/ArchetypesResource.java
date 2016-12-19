package cz.zcu.kiv.sehr.resources;

import cz.zcu.kiv.sehr.archetypes.ArchetypeParser;
import cz.zcu.kiv.sehr.database.MongoDBConnector;
import cz.zcu.kiv.sehr.utils.Config;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.openehr.am.archetype.Archetype;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by ghessova on 17.12.16.
 */
@Path("archetypes")
public class ArchetypesResource {

    /** The path to the folder where we want to store the uploaded files */
    private static final String UPLOAD_FOLDER = "uploaded/";


    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response listArchetypes(@HeaderParam("from") String from, @HeaderParam("count") String count) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArchetype(@HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    public Response listRequests(@HeaderParam("from") String from, @HeaderParam("size") String size, @HeaderParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
    public Response requestAddingArchetype(@HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("request")
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
    public Response uploadArchetype(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
        /*// check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("Invalid form data").build();
        // create our destination folder, if it not exists
        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            return Response.status(500)
                    .entity("Can not create destination folder on server")
                    .build();
        }
        String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            return Response.status(500).entity("Can not save file").build();
        }
        return Response.status(200)
                .entity("File saved to " + uploadedFileLocation).build();*/
        try {
            ArchetypeParser archetypeParser = new ArchetypeParser();
            Document document = archetypeParser.processArchetypeInputStream(uploadedInputStream);
            Config.getDBC().addDocument(document);
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

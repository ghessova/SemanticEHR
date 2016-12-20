package cz.zcu.kiv.sehr.resources;

import cz.zcu.kiv.sehr.model.DocumentWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by ghessova on 19.12.16.
 * Service for managing documents representing user data
 */
@Path("documents")
@Api(value="documents", description="Service for managing documents.")
public class DocumentsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds documents", response = DocumentWrapper.class)
    public Response getDocuments(@QueryParam("documentId") String documentId) {
        //todo
        return null;
    }

    @GET
    @Path("{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds document with specific ID", response = DocumentWrapper.class)
    public Response getDocument(@PathParam("documentId") String documentId) {
        //todo
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Deletes document with specific ID")
    public Response deleteDocument(@QueryParam("documentId") String documentId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Submits new document containing user data")
    public Response uploadDocument(@FormDataParam("file") InputStream uploadedInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetail,
                                   @QueryParam("name") String name,
                                   @QueryParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();

    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON) //userId will be used e.g. by data sharing
    public Response listDocuments(@QueryParam("from") String from, @QueryParam("count") String count, @QueryParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Path("share")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Shares specified document with specified user")
    public Response uploadDocument(@QueryParam("documentId") String documentId,
                                   @QueryParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();

    }

}

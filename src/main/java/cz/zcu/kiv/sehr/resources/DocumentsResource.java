package cz.zcu.kiv.sehr.resources;

import cz.zcu.kiv.sehr.model.DataDocument;
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
 * Service managing user data
 */
@Path("documents")
@Api(value="documents", description="Service for managing documents.")
public class DocumentsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds document with specific ID", response = DataDocument.class)
    public DataDocument getDocument(@HeaderParam("documentId") String documentId) {
        //todo
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDocument(@HeaderParam("documentId") String documentId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadDocument(@FormDataParam("file") InputStream uploadedInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetail,
                                   @HeaderParam("name") String name,
                                   @HeaderParam("archetypeId") String archetypeId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();

    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON) //userId will be used e.g. by data sharing
    public Response listDocuments(@HeaderParam("from") String from, @HeaderParam("count") String count, @HeaderParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();
    }

    /**
     * service for sharing specific document with specific user
     */
    @POST
    @Path("share")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response uploadDocument(@HeaderParam("documentId") String documentId,
                                   @HeaderParam("userId") String userId) {
        //todo
        return Response
                .status(200)
                .entity(new Document("todo", true).toJson())
                .build();

    }

}

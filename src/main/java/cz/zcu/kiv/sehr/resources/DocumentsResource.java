package cz.zcu.kiv.sehr.resources;

import com.mongodb.util.JSON;
import cz.zcu.kiv.sehr.dao.DocumentsDAO;
import cz.zcu.kiv.sehr.model.DocumentWrapper;
import cz.zcu.kiv.sehr.model.UserWrapper;
import cz.zcu.kiv.sehr.services.AuthenticationService;
import cz.zcu.kiv.sehr.utils.PagingParams;
import cz.zcu.kiv.sehr.utils.Utils;
import io.swagger.annotations.*;
import org.bson.Document;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static cz.zcu.kiv.sehr.resources.ArchetypesResource.DEFAULT_LIMIT;

/**
 * Created by ghessova on 19.12.16.
 * Service for managing documents representing user data
 */
@Path("documents")
@Api(value="documents", description="Service for managing documents.")
public class DocumentsResource {

    private DocumentsDAO documentsDAO = DocumentsDAO.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds documents by archetypeId or for a specific user", response = DocumentWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 403, message = "Invalid access token") })
    public Response getDocuments(@HeaderParam("token") String token, @QueryParam("archetypeId") String archetypeId, @QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + DEFAULT_LIMIT) String count) {
        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserWrapper user = AuthenticationService.getInstance().findUser(token);
        List<Document> results = documentsDAO.findDocuments(archetypeId, user, pagingParams);

        return Response
                .status(Response.Status.OK)
                .entity(JSON.serialize(results))
                .build();
    }

    @GET
    @Path("{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Finds document with specific ID", response = DocumentWrapper.class)
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "auth token", required = true, dataType = "string", paramType = "header")
    })
    public Response getDocument(@PathParam("documentId") String documentId) {
        Document result =  documentsDAO.findDocumentById(documentId);
        if (result == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(result.toJson()).build();
        }
    }

    @DELETE
    @Path("{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Deletes document with specific ID")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "auth token", required = true, dataType = "string", paramType = "header")
    })
    public Response deleteDocument(@PathParam("documentId") String documentId) {
        long deleted = documentsDAO.deleteDocumentById(documentId);

        if (deleted > 0) {
            return Response.status(200).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Submits new document containing user data")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad parameters"), @ApiResponse(code = 403, message = "Invalid access token") } )
    public Response uploadDocument(@HeaderParam("token") String token, Document document, @QueryParam("name") String name, @QueryParam("archetypeId") String archetypeId){
        UserWrapper user = AuthenticationService.getInstance().findUser(token);
        long added = documentsDAO.insertDocument(user, name, archetypeId, document);
        if(added > 0)
            return Response.status(200).build();
        else
            return Response.status(400).build();

    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON) //userId will be used e.g. by data sharing
    @ApiOperation(value="Lists basic info about documents", response = DocumentWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response listDocuments(@HeaderParam("token") String token, @QueryParam("from") String from, @QueryParam("count") String count, @QueryParam("archetypeId") String archetypeId) {
        UserWrapper user = AuthenticationService.getInstance().findUser(token);
        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<DocumentWrapper> results = documentsDAO.listDocuments(archetypeId, user, pagingParams);
        return Response
                .status(200)
                .entity(JSON.serialize(results))
                .build();
    }

    @GET
    @Path("share")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Lists shared documents", response = DocumentWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response getSharedDocuments(@HeaderParam("token") String token, @QueryParam("from") String from, @QueryParam("count") String count) {
        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserWrapper user = AuthenticationService.getInstance().findUser(token);
        List<Document> results = documentsDAO.findDocumentsByFieldValue("sharedWith", user.getUsername(), pagingParams);

        return Response
                .status(Response.Status.OK)
                .entity(JSON.serialize(results))
                .build();
    }

    @POST
    @Path("share")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Shares specified document with specified user")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response shareDocument(@HeaderParam("token") String token, @QueryParam("documentId") String documentId,
                                   @QueryParam("username") String username) {
        Document document = documentsDAO.findDocumentById(documentId);
        if(document == null)
            return Response.status(404).build();
        List<String> users = (List<String>)document.get("sharedWith");
        if(!users.contains(username))
        {
            users.add(username);
            documentsDAO.editDocument(documentId, document.toJson());
        }

        return Response
                .status(200)
                .build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    @ApiOperation(value="Searches for documents by keyword", response = DocumentWrapper.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 403, message = "Invalid access token") })
    public Response searchArchetypes(@HeaderParam("token") String token, @QueryParam("keyword") @DefaultValue("0") String keyword, @QueryParam("from") @DefaultValue("0") String from, @QueryParam("count") @DefaultValue("" + DEFAULT_LIMIT) String count) {

        PagingParams pagingParams = Utils.processPagingParams(from, count);
        if (pagingParams == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<Document> results = documentsDAO.searchDocuments(keyword, pagingParams);

        return Response
                .status(Response.Status.OK)
                .entity(JSON.serialize(results))
                .build();

    }

}

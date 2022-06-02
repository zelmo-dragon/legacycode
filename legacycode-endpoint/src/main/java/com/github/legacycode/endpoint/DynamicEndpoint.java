package com.github.legacycode.endpoint;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@RequestScoped
@Path("entity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DynamicEndpoint {

    @Inject
    private EntryManager entryManager;

    public DynamicEndpoint() {
    }

    @GET
    @Path("{entity}")
    public Response filter(
            @PathParam("entity") final String entity,
            @Context final UriInfo info) {

        var rawQueries = info.getQueryParameters();
        var queries = Queries.extractQueries(rawQueries);
        var service = entryManager.invokeService(entity);
        var paginationData = service.onFilter(entity, queries);
        return Response.ok(paginationData).build();
    }

    @GET
    @Path("{entity}/{id}")
    public Response find(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id) {
        var service = entryManager.invokeService(entity);
        var data = service.onFind(entity, id);
        return Response.ok(data).build();
    }

    @POST
    @Path("{entity}")
    public Response create(
            @PathParam("entity") final String entity,
            final JsonObject data) {

        var service = entryManager.invokeService(entity);
        var uri = service.onCreate(entity, data);
        return Response.created(uri).build();
    }

    @PUT
    @Path("{entity}/{id}")
    public Response update(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id,
            final JsonObject data) {

        var service = entryManager.invokeService(entity);
        service.onUpdate(entity, data, id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{entity}/{id}")
    public Response delete(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id) {

        var service = entryManager.invokeService(entity);
        service.onDelete(entity, id);
        return Response.noContent().build();
    }


}

package com.github.legacycode.jakarta.dynamic;

import jakarta.enterprise.context.RequestScoped;
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
@Path("dynamic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DynamicEndpoint {

    public DynamicEndpoint() {
    }

    @GET
    @Path("{entity}")
    public Response filter(
            @PathParam("entity") final String entity,
            @Context final UriInfo query) {

        return Response.ok().build();
    }

    @GET
    @Path("{entity}/{id}")
    public Response find(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id) {

        return Response.ok().build();
    }

    @POST
    @Path("{entity}")
    public Response create(
            @PathParam("entity") final String entity,
            final JsonObject data) {

        return Response.noContent().build();
    }

    @PUT
    @Path("{entity}/{id}")
    public Response update(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id,
            final JsonObject data) {

        return Response.noContent().build();
    }

    @DELETE
    @Path("{entity}/{id}")
    public Response delete(
            @PathParam("entity") final String entity,
            @PathParam("id") final String id) {

        return Response.noContent().build();
    }


}

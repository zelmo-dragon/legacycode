package com.github.legacycode.endpoint.internal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.json.Json;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import com.github.legacycode.endpoint.ActionDeniedException;

@Provider
public class ActionDeniedExceptionMapper implements ExceptionMapper<ActionDeniedException> {

    public ActionDeniedExceptionMapper() {
    }

    @Override
    public Response toResponse(final ActionDeniedException exception) {

        var json = Json.createObjectBuilder()
                .add("error", exception.getClass().getName())
                .add("message", exception.getMessage())
                .add("date", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(json)
                .build();
    }
}

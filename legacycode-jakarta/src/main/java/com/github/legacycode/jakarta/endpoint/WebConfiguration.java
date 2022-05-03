package com.github.legacycode.jakarta.endpoint;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
public class WebConfiguration extends Application {

    public WebConfiguration() {
    }
}

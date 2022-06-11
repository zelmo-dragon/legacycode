package com.github.legacycode.glassfish;

import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Glassfish {

    private static final Logger LOGGER = Logger.getLogger(Glassfish.class.getName());

    public Glassfish() {
    }

    public void start(@Observes @Initialized(ApplicationScoped.class) final Object pointless) {

        var message = """
                ==============================
                    Legacy Code
                    Java:
                        Home: {{java.home}}
                        Vendor: {{java.vendor}}
                        Vendor URL: {{java.vendor.url}}
                        Version: {{java.version}}
                        Class path: {{java.class.path}}
                    User:
                        Name: {{user.name}}
                        Home: {{user.home}}
                        Dir: {{user.dir}}
                    OS:
                        Name: {{os.name}}
                        Arch: {{os.arch}}
                        Version: {{os.version}}
                ==============================
                """;

        message = message
                .replace("{{java.home}}", System.getProperty("java.home"))
                .replace("{{java.vendor}}", System.getProperty("java.vendor"))
                .replace("{{java.vendor.url}}", System.getProperty("java.vendor.url"))
                .replace("{{java.version}}", System.getProperty("java.version"))
                .replace("{{java.class.path}}", System.getProperty("java.class.path"))
                .replace("{{user.name}}", System.getProperty("user.name"))
                .replace("{{user.home}}", System.getProperty("user.home"))
                .replace("{{user.dir}}", System.getProperty("user.dir"))
                .replace("{{os.name}}", System.getProperty("os.name"))
                .replace("{{os.arch}}", System.getProperty("os.arch"))
                .replace("{{os.version}}", System.getProperty("os.version"));

        LOGGER.info(message);
    }
}

package com.github.mxab.demo;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("/")
public class IndexPage {

    private final Template index;

    public IndexPage(Template index) {
        this.index = requireNonNull(index, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return index.data("name", name);
    }

}

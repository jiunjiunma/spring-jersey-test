package example.rest;

import java.util.Collection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.model.Sample;
import example.service.SampleService;

/**
 * Date: 6/10/13
 * Time: 5:25 PM
 */
@Component
@Path("/sample")
public class SampleResource {
    @Autowired
    private SampleService sampleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path ("/{id}")
    public Sample getSample(@PathParam("id") int id) {
        Sample sample = sampleService.getSample(id);
        if (sample == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return sample;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sample> getSamples() {
        return sampleService.getSamples();
    }
}

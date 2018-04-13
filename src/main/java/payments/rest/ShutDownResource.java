package payments.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("shutdown")
public class ShutDownResource {

    private static Logger log = LoggerFactory.getLogger(ShutDownResource.class);
    //ex. curl -X POST localhost:8888/payments/api/v1.0/shutdown
    @POST
    public void shutDown() {
        log.info("Server shutdown requested. Sayonara world!");
        System.out.println("Server shutdown requested. Sayonara world!");
        System.exit(0);
    }
}

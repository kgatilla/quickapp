package payments.rest;


import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("shutdown")
public class ShutDownResource {
    //curl -X POST localhost:8888/payments/api/v1.0/shutdown
    @POST
    public void shutDown() {
        System.out.println("Server shutdown requested. Sayōnara world!");
        System.exit(0);
    }
}

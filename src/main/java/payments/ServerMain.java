package payments;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class ServerMain {

    private static Logger log = LoggerFactory.getLogger(ServerMain.class);

    private static final String BASE_URI = "http://localhost:8888/payments/api/v1.0/";

    private HttpServer createServer() {
        final ResourceConfig rc = new ResourceConfig().packages("payments.rest");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    private void startServer(HttpServer server) {
        try {
            log.info("\"Jersey app starting with WADL available at {}sapplication.wadl\\\nStop it with <curl -X POST {}shutdown>\\",
                    BASE_URI, BASE_URI);
            System.out.println(String.format("Jersey app starting with WADL available at %sapplication.wadl"+
                    "\nStop it with <curl -X POST %shutdown>", BASE_URI, BASE_URI));
            server.start();
        } catch (IOException e) {
            log.error("Grizzly server start error:{}",e.toString());
            e.printStackTrace();
        }
    }

    public void main(String[] args)  {

        final HttpServer server = createServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        startServer(server);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            log.error("Server exception:", e.toString());
            e.printStackTrace();
        }
    }
}

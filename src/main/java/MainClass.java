import payments.datastore.H2DBUtilities;
import payments.ServerMain;

import java.io.IOException;

public class MainClass {


    public static void main(String... args) {
        ServerMain server =  new ServerMain();
        server.main(args);
    }
}

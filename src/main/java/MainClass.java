import payments.datastore.H2DBUtilities;
import payments.rest.ServerMain;

import java.io.IOException;

public class MainClass {


    private static void spinUpH2DB() {
        H2DBUtilities util = new H2DBUtilities();
        util.createNewDbFromScratch();
    }

    public static void main(String... args) {
        //spinUpH2DB();
        //while(true){}

        //RESTAccountsService s = new RESTAccountsService();
        //s.setupClientAccountHolder("James", "Bond","surreal@gmail.com");
        //s.setupNewClientBankAccount(1,"BIC1","IBAN1","010101","01010101","USD");

        try {
            ServerMain.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

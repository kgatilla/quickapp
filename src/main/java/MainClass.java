import datastore.H2DBUtilities;

public class MainClass {


    private static void spinUpH2DB() {
        H2DBUtilities util = new H2DBUtilities();
        util.createNewDbFromScratch();
    }

    public static void main(String... args) {
        //spinUpH2DB();

        //while(true){}
    }
}

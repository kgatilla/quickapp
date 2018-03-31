package payments.datastore;

import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Optional;

public class H2DBUtilities {
    /*
JDBC driver class: org.h2.Driver
Database URL: jdbc:h2:tcp://localhost/~/test
*/
    //--- start the H2 DB?
    public Optional<Connection> createNewDbFromScratch(){
        return createNewDb()
                .flatMap(this::startDb)
                .flatMap(s->getDBConnection())
                .flatMap(this::dropTables)
                .flatMap(this::createTables);
    }

    //--- create new H2 in memory TCP server
    private String[] setupDbArgs() {
        return new String[]{"-tcpPort", "9123", "-tcpAllowOthers"};
    }

    private Optional<Server> createNewDb(){
        String[] args = setupDbArgs();
        Server server = null;
        try {
            server = Server.createTcpServer(args);
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return Optional.ofNullable(server);
    }

    private Optional<Server> startDb(Server server) {
        if (server.isRunning(false)) {
            try {
                server.start();
            } catch (SQLException e) {
                System.out.println("Exception Message " + e.getLocalizedMessage());
                e.printStackTrace();
                server = null;
            }
        }

        return Optional.ofNullable(server);
    }

    //--- connect to the H2 DB
    private Optional<Connection> getDBConnection() {
        final String DB_DRIVER = "org.h2.Driver";
        final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        final String DB_USER = "";
        final String DB_PASSWORD = "";

        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (dbConnection!=null && dbConnection.getAutoCommit())
                dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception Message " + e.getLocalizedMessage());
            try {
                dbConnection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("Exception Message " + e.getLocalizedMessage());
                dbConnection = null;
            }
        }

        return Optional.ofNullable(dbConnection);
    }

    //--- create H2 DB tables
    private Optional<Connection> dropTables(Connection connection)  {
        return executeCreateStatement(SQL_COMMANDS.SQL_DROP_ACCOUNT_HOLDER_TABLE, connection)
                .flatMap(con->executeCreateStatement(SQL_COMMANDS.SQL_DROP_ACCOUNTS_TABLE,con))
                .flatMap(con->executeCreateStatement(SQL_COMMANDS.SQL_DROP_BANK_TRANSACTIONS_TABLE, con));
    }

    private Optional<Connection> createTables(Connection connection) {

        return executeCreateStatement(SQL_COMMANDS.SQL_CREATE_ACCOUNT_HOLDER_TABLE, connection)
                .flatMap(con->executeCreateStatement(SQL_COMMANDS.SQL_CREATE_ACCOUNTS_TABLE,con))
                .flatMap(con->executeCreateStatement(SQL_COMMANDS.SQL_CREATE_BANK_TRANSACTIONS_TABLE, con));
    }

    private Optional<Connection> executeCreateStatement(String sql, Connection connection) {
        Connection resConnection = null;
        try {
            PreparedStatement createPreparedStatement = connection.prepareStatement(sql);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
            connection.commit();
            resConnection = connection;
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(resConnection);
    }

}

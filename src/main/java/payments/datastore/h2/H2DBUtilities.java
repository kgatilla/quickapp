package payments.datastore.h2;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class H2DBUtilities {

    private static Logger log = LoggerFactory.getLogger(H2DBUtilities.class);
    /*
JDBC driver class: org.h2.Driver
Database URL: jdbc:h2:tcp://localhost/~/test
*/
    //--- start the ORM DB?
    public Optional<Connection> createNewDbFromScratch(){
        return createNewDb()
                .flatMap(this::startDb)
                .flatMap(s->getDBConnection())
                .flatMap(this::dropTables)
                .flatMap(this::createTables);
    }

    //--- create new ORM in memory TCP server
    private String[] setupDbArgs() {
        return new String[]{"-tcpPort", "9123", "-tcpAllowOthers"};
    }

    private Optional<Server> createNewDb(){
        String[] args = setupDbArgs();
        Server server = null;
        try {
            server = Server.createTcpServer(args);
        } catch (SQLException e) {
            log.error("createNewDb exception:\n errorCode={},\n toString={},\n sqlState={}",e.getErrorCode(), e.toString(),e.getSQLState());
            e.printStackTrace();
        }

        return Optional.ofNullable(server);
    }

    private Optional<Server> startDb(Server server) {
        if (server.isRunning(false)) {
            try {
                server.start();
            } catch (SQLException e) {
                log.error("startDB exception:\n errorCode={},\n toString={},\n sqlState={}", e.getErrorCode(), e.toString(), e.getSQLState());
                e.printStackTrace();
                server = null;
            }
        }

        return Optional.ofNullable(server);
    }

    //--- connect to the ORM DB
    private Optional<Connection> getDBConnection() {
        final String DB_DRIVER = "org.h2.Driver";
        final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        final String DB_USER = "";
        final String DB_PASSWORD = "";

        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            log.error ("getDBConnection:", e.toString());
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            log.error("getConnection sql exception:\n errorCode={},\n toString={},\n sqlState={}", e.getErrorCode(), e.toString(), e.getSQLState());
            e.printStackTrace();
        }

        try {
            if (dbConnection!=null && dbConnection.getAutoCommit())
                dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error(" setAutoCommit exception:\n errorCode={},\n toString={},\n sqlState={}", e.getErrorCode(), e.toString(), e.getSQLState());
            e.printStackTrace();
            try {
                dbConnection.close();
            } catch (SQLException e1) {
                log.error(" db close exception:\n errorCode={},\n toString={},\n sqlState={}", e1.getErrorCode(), e1.toString(), e1.getSQLState());
                e1.printStackTrace();
                dbConnection = null;
            }
        }

        return Optional.ofNullable(dbConnection);
    }

    //--- create ORM DB tables
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
            log.error(" executeCreateStatement sql exception:\n errorCode={},\n toString={},\n sqlState={}", e.getErrorCode(), e.toString(), e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("executeCreateStatement exception: {}", e.toString());
            e.printStackTrace();
        }

        return Optional.ofNullable(resConnection);
    }

}

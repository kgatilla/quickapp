package datastore;

import java.util.Arrays;
import java.util.List;

class SQL_COMMANDS {

    //Table creation scripts
    public final static String SQL_CREATE_ACCOUNT_HOLDER_TABLE = "CREATE TABLE ACCOUNT_HOLDER (" +
            "id int primary key, " +
            "first_name varchar(255), " +
            "last_name varchar(255), " +
            "email varchar(255))";

    public final static String SQL_CREATE_ACCOUNTS_TABLE = "CREATE TABLE BANK_ACCOUNTS (" +
            "id int primary key, " +
            "bic varchar(11), " +
            "iban varchar(34), " +
            "sort_code varchar(6), " +
            "account_no varchar(8))";

    public final static String SQL_CREATE_BANK_TRANSACTIONS_TABLE = "CREATE TABLE TRANSACTIONS (" +
            "id int primary key, " +
            "payerAccountId int, " +
            "payeeAccountId int, " +
            "transferAmount double, " +
            "transferCurrencyISOCode varchar(3), " +
            "setup_date date, " +
            "transaction_date date, " +
            "account_no varchar(8))";

    //Table drop scripts
    public final static String SQL_DROP_ACCOUNT_HOLDER_TABLE = "DROP TABLE IF EXISTS ACCOUNT_HOLDER";
    public final static String SQL_DROP_ACCOUNTS_TABLE = "DROP TABLE IF EXISTS BANK_ACCOUNTS";
    public final static String SQL_DROP_BANK_TRANSACTIONS_TABLE = "DROP TABLE IF EXISTS TRANSACTIONS";


}

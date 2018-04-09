package payments.rest;

class RESPONSE_TEXTS {
    static final String RESPONSE_GET_ALL_ACCOUNT_HOLDERS = "Account holder ID's in the system:";
    static final String RESPONSE_ERROR_GET_ACCOUNTS_HOLDER = "Could not find account holder with id:";
    static final String RESPONSE_ERROR_CREATE_ACCOUNT_HOLDER = "Error creating new Account holder!";

    static final String RESPONSE_ERROR_GET_ACCOUNTS_FOR_HOLDER = "Could not find bank accounts for account holder with id:";
    static final String RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER = "Account creation failed for account holder id:";

    static final String RESPONSE_ERROR_CREATE_NEW_TRANSACTION_DATE_FORMAT = "<transactionDate> must have YYYYMMDD format.";
    static final String RESPONSE_ERROR_CREATE_NEW_TRANSACTION = "Transaction setup failed.";
    static final String RESPONSE_ERROR_GET_ACCOUNT_FOR_ACCOUNT_ID = "Could not find bank account with id:";
    static final String RESPONSE_SUMMARY_TRANSACTIONS = "Inflows into the account: %.2f \n Outflows from the account:%.2f";

}

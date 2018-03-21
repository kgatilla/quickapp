package service;

public interface AccountsService {
    //client setup operations
    void setupClientAccountHolder(String firstName, String lastName, String email);

    //client Account administration
    void setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    void fetchClientBankAccounts(int clientId);

    //payee account holder operations
    @Deprecated //aka unimplemented
    void setupPayeeAccountHolder(int clientId, String payeeFirstName, String payeeLastName, String email);

    @Deprecated //aka unimplemented
    void fetchPayeeAccountHoldersForClient(int clientId);


    //payee account operations
    @Deprecated //aka unimplemented
    void setupClientPayeeAccount(int clientId, int payeeId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    @Deprecated //aka unimplemented
    void fetchPayeeAccountsForClient(int clientId);

}

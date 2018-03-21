package service;

public interface AccountsService {
    //client setup operations
    void setupClientAccountHolder(String firstName, String lastName, String email);

    //client Account administration
    void setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    void fetchClientBankAccounts(int clientId);

    //payee account holder operations
    void setupPayeeAccountHolder(int clientId, String payeeFirstName, String payeeLastName, String email);
    void fetchPayeeAccountHoldersForClient(int clientId);


    //payee account operations
    void setupClientPayeeAccount(int clientId, int payeeId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    void fetchPayeeAccountsForClient(int clientId);

}

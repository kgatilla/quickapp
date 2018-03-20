package service;

public interface AccountsService {
    //client setup operations
    void setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email);

    //client Account administration
    void setupNewClientBankAccount(long cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    void fetchClientBankAccounts(long clientId);

    //payee account holder operations
    void setupPayeeAccountHolder(long clientId, String payeeFirstName, String payeeLastName, String email);
    void fetchPayeeAccountHoldersForClient(long clientId);


    //payee account operations
    void setupClientPayeeAccount(long clientId, long payeeId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    void fetchPayeeAccountsForClient(long clientId);

}

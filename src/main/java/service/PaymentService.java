package service;

/**
 * Interfaces for Payment Services entry point
 */
public interface PaymentService {

    //transaction operations
    void setupNewTransaction(long clientId, long payerBankAccountId, String currency, double amount, String executionDate);
    void fetchTransactionsForClient(long clientId);
}

package service;

public interface DepositService {

    void depositToClientBankAccount(long clientId, long accountId, double amount);
}

package service;

public interface BalanceCheckService {

    void getAccountBalanceAt(long clientId,long accountID, String date);
    void getAccountBalanceNow(long clientId,long accountID);
}

package dao;

public class BankAccountsDAOProvider {

    public BankAccountsDAO getBankAccountsDAONoDB() {
        return BankAccountsDAONoDB.getInstance();
    }
}

package dao;

public class BankAccountHolderDAOProvider {

    public BankAccountHolderDAO getBankAccountHolderDAONoDB() {
        return BankAccountHolderDAONoDB.getInstance();
    }
}

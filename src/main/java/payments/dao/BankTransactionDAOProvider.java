package payments.dao;

public class BankTransactionDAOProvider {
    public BankTransactionDAO getBankTransationDAONoDB(){
        return BankTransactionDAONoDB.getInstance();
    }
}

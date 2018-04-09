package payments.dao;

import payments.model.BankTransaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class BankTransactionDAONoDB implements BankTransactionDAO {

    //Singleton
    private static class Helper{
        private static final BankTransactionDAONoDB INSTANCE = new BankTransactionDAONoDB();
    }

    public static BankTransactionDAONoDB getInstance() {
        return Helper.INSTANCE;
    }

    private BankTransactionDAONoDB() {}

    @Override
    public Optional<BankTransaction> setupNewTransaction(int payerAccountId, int payeeAcountId, double transferAmount, String currencyISOCode, LocalDate transferDate) {
        return Optional.empty();
    }

    @Override
    public Optional<BankTransaction> getTransactionById(int transactionId) {
        return Optional.empty();
    }

    private final HashSet<BankTransaction> transactions = new HashSet<>();
    private final HashMap<Integer, HashSet<BankTransaction>> transactionsByPayee = new HashMap<>();

}

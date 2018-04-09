package payments.dao;

import payments.model.BankTransaction;

import java.time.LocalDate;
import java.util.Optional;

public interface BankTransactionDAO {

    Optional<BankTransaction> setupNewTransaction(int payerAccountId, int payeeAcountId, double transferAmount, String currencyISOCode, LocalDate transferDate);
    Optional<BankTransaction> getTransactionById(int transactionId);
}

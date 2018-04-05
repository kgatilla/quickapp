package payments.model;

import org.joda.money.Money;

import java.time.LocalDate;

public interface BankTransaction {

    int getTransactionId();

    BankAccount getPayerAccount();
    BankAccount getPayeeAccount();

    Money getTransferAmount();

    LocalDate getSetupDate();
    LocalDate getTransactionDate();
}

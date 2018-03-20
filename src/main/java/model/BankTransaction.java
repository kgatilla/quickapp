package model;

import org.joda.money.Money;

import java.time.LocalDate;

public interface BankTransaction {

    long getTransactionId();

    BankAccount getPayerAccount();
    BankAccount getPayeeAccount();

    Money getTransferAmmount();

    LocalDate getSetupDate();
    LocalDate getTransactionDate();
}

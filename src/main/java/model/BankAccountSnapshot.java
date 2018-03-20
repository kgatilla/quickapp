package model;

import org.joda.money.Money;

import java.time.LocalDateTime;

/**
 * Interface to model internal account states
 */
public interface BankAccountSnapshot {

    BankAccount getBankAccount();
    Money getBalanceAmount();
    LocalDateTime getSnapshotTimeStamp();

}

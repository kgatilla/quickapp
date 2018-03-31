package payments.model;

import org.joda.money.Money;

import java.time.LocalDateTime;

/**
 * Interface to payments.service.model internal account states
 */
public interface BankAccountSnapshot {

    BankAccount getBankAccount();
    Money getBalanceAmount();
    LocalDateTime getSnapshotTimeStamp();

}

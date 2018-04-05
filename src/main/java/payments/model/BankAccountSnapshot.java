package payments.model;

import org.joda.money.Money;

import java.time.LocalDateTime;

public interface BankAccountSnapshot {

    BankAccount getBankAccount();
    Money getBalanceAmount();
    LocalDateTime getSnapshotTimeStamp();

}

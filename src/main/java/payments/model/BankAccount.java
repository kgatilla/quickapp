package payments.model;

import org.joda.money.CurrencyUnit;

import java.util.Set;

public interface BankAccount {

    int getAccountId();

    String getBIC();
    String getIBAN();
    String getUKSortCode();
    String getUKAccountNumber();

    CurrencyUnit getCurrency();

    // for joint accounts
    Set<BankAccountHolder> getOwners();
}

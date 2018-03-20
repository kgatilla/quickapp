package model;

import org.joda.money.CurrencyUnit;

import java.util.Set;

/**
 * Interface to model internal or external bank accounts
 */
public interface BankAccount {

    long getAccountId();

    String getBIC();
    String getIBAN();
    String getUKSortCode();
    String getUKAccountNumber();

    CurrencyUnit getCurrency();

    //Catter for joint accounts
    Set<BankAccountHolder> getOwners();
}

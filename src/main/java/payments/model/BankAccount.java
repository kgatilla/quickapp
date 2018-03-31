package payments.model;

import org.joda.money.CurrencyUnit;

import java.util.Set;

/**
 * Interface to payments.service.model internal or external bank accounts
 */
public interface BankAccount {

    int getAccountId();

    String getBIC();
    String getIBAN();
    String getUKSortCode();
    String getUKAccountNumber();

    CurrencyUnit getCurrency();

    //Catter for joint accounts
    Set<BankAccountHolder> getOwners();
}

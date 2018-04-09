package payments.model;

import org.joda.money.CurrencyUnit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class BankAccount {

    public static BankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, final Set<BankAccountHolder> owners) {
        return new BankAccount(accountId,bic,iban,ukSortCode,ukAccountNumber,currency, owners);
    }


    public static BankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, BankAccountHolder owner) {
        HashSet<BankAccountHolder> owners = new HashSet<>();
        owners.add(owner);
        return new BankAccount(accountId,bic,iban,ukSortCode,ukAccountNumber,currency, owners);
    }

    private BankAccount(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, final Set<BankAccountHolder> owners) {
        this.accountId = accountId;
        this.BIC = bic;
        this.IBAN = iban;
        this.UKSortCode = ukSortCode;
        this.UKAccountNumber = ukAccountNumber;
        this.currency = currency;
        this.owners = new HashSet<>();
        this.owners.addAll(owners);
    }

    public int getAccountId() {
        return this.accountId;
    }

    public String getBIC() {
        return this.BIC;
    }

    public String getIBAN() {
        return this.IBAN;
    }

    public String getUKSortCode() {
        return this.UKSortCode;
    }

    public String getUKAccountNumber() {
        return this.UKAccountNumber;
    }

    public CurrencyUnit getCurrency() {
        return this.currency;
    }

    public Set<BankAccountHolder> getOwners() {
        //TODO: consider defensive copy
        return Collections.unmodifiableSet(this.owners);
    }

    private final int accountId;
    private final String BIC;
    private final String IBAN;
    private final String UKSortCode;
    private final String UKAccountNumber;
    private final CurrencyUnit currency;
    private final Set<BankAccountHolder> owners;

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (null == other) return false;
        if (! (other instanceof BankAccount)) return false;
        return this.accountId == ((BankAccount) other).getAccountId();
    }

    @Override
    public int hashCode(){
        return accountId;
    }
}

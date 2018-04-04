package payments.model;

import org.joda.money.CurrencyUnit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class InternalBankAccount implements BankAccount {

    public static InternalBankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, final Set<BankAccountHolder> owners) {
        return new InternalBankAccount(accountId,bic,iban,ukSortCode,ukAccountNumber,currency, owners);
    }


    public static InternalBankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, BankAccountHolder owner) {
        HashSet<BankAccountHolder> owners = new HashSet<>();
        owners.add(owner);
        return new InternalBankAccount(accountId,bic,iban,ukSortCode,ukAccountNumber,currency, owners);
    }

    private InternalBankAccount(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, final Set<BankAccountHolder> owners) {
        this.accountId = accountId;
        this.BIC = bic;
        this.IBAN = iban;
        this.UKSortCode = ukSortCode;
        this.UKAccountNumber = ukAccountNumber;
        this.currency = currency;
        this.owners = new HashSet<>();
        this.owners.addAll(owners);
    }

    @Override
    public int getAccountId() {
        return this.accountId;
    }

    @Override
    public String getBIC() {
        return this.BIC;
    }

    @Override
    public String getIBAN() {
        return this.IBAN;
    }

    @Override
    public String getUKSortCode() {
        return this.UKSortCode;
    }

    @Override
    public String getUKAccountNumber() {
        return this.UKAccountNumber;
    }

    @Override
    public CurrencyUnit getCurrency() {
        return this.currency;
    }

    @Override
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

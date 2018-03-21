package model;

import org.joda.money.CurrencyUnit;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public final class InternalBankAccount implements BankAccount {

    public static InternalBankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, final Set<BankAccountHolder> owners) {
        return new InternalBankAccount(accountId,bic,iban,ukSortCode,ukAccountNumber,currency, owners);
    }


    public static InternalBankAccount of(int accountId, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency, BankAccountHolder owner) {
        TreeSet<BankAccountHolder> owners = new TreeSet<>();
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
        this.owners = new TreeSet<>();
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
}

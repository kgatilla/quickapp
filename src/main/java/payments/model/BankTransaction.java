package payments.model;

import org.joda.money.Money;

import java.time.LocalDate;

public class BankTransaction {

    public BankTransaction(int transactionId, BankAccount payerAccount, BankAccount payeeAccount,
                           Money transferAmount, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.payerAccount = payerAccount;
        this.payeeAccount = payeeAccount;
        this.transferAmount = transferAmount;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public BankAccount getPayerAccount(){
        return this.payerAccount;
    }

    public BankAccount getPayeeAccount(){
        return this.payeeAccount;
    }

    public Money getTransferAmount(){
        return this.transferAmount;
    }

    public LocalDate getTransactionDate(){
        return this.transactionDate;
    }

    private int transactionId;
    private final BankAccount payerAccount;
    private final BankAccount payeeAccount;
    private final Money transferAmount;
    private final LocalDate transactionDate;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (null == other) return false;
        if (! (other instanceof BankTransaction)) return false;
        return this.transactionId == ((BankTransaction) other).getTransactionId();
    }

    @Override
    public int hashCode() {
        return this.transactionId;
    }
}

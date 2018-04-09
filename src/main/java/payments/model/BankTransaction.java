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

    int getTransactionId() {
        return this.transactionId;
    }

    BankAccount getPayerAccount(){
        return this.payerAccount;
    }
    BankAccount getPayeeAccount(){
        return this.payeeAccount;
    }

    Money getTransferAmount(){
        return this.transferAmount;
    }

    LocalDate getTransactionDate(){
        return this.transactionDate;
    }

    private int transactionId;
    private final BankAccount payerAccount;
    private final BankAccount payeeAccount;
    private final Money transferAmount;

    private final LocalDate transactionDate;
}

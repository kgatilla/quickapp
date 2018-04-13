package payments.dao;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payments.model.BankAccount;
import payments.model.BankTransaction;
import payments.util.MoneyBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BankTransactionDAONoDB implements BankTransactionDAO {

    private static Logger log = LoggerFactory.getLogger(BankTransactionDAONoDB.class);
    //Singleton
    private static class Helper{
        private static final BankTransactionDAONoDB INSTANCE = new BankTransactionDAONoDB();
    }

    static BankTransactionDAONoDB getInstance() {
        return Helper.INSTANCE;
    }

    private BankTransactionDAONoDB() {}

    @Override
    public Optional<BankTransaction> setupNewTransaction(int payerAccountId, int payeeAccountId, double transferAmount,
                                                         String currencyISOCode, LocalDate transferDate) {
        log.debug("setupNewTransaction: payerAccountId={}, payeeAccountId={}, transferAmount={}, currencyISOCode={}, transferDate={}"
                ,payerAccountId, payeeAccountId, transferAmount, currencyISOCode, transferDate.format(DateTimeFormatter.BASIC_ISO_DATE));

        return fetchAccountForId(payerAccountId).flatMap(
                payer-> fetchAccountForId(payeeAccountId).flatMap(
                    payee -> MoneyBuilder.of(transferAmount,currencyISOCode).flatMap(
                        money-> synchronizedCreateBankTransfer(payer, payee, money, transferDate))));

    }

    @Override
    public Optional<BankTransaction> getTransactionById(int transactionId) {
        log.debug("getTransactionById:{}",transactionId);
        return Optional.empty();
    }

    @Override
    public Set<BankTransaction> transactionSummary(int bankAccountId, LocalDate summaryStartDate, LocalDate summaryEndDate) {
        log.debug("transactionSummary: bankAccountId={}, summaryStartDate={}, summaryEndDate={}", bankAccountId, summaryStartDate, summaryEndDate);
        return transactions.stream()
                .filter( tr-> (tr.getPayeeAccount().getAccountId() == bankAccountId || tr.getPayerAccount().getAccountId() == bankAccountId) &&
                        (tr.getTransactionDate().isAfter(summaryStartDate) && tr.getTransactionDate().isBefore(summaryEndDate)))
                .collect(Collectors.toSet());
    }

    private final HashSet<BankTransaction> transactions = new HashSet<>();
    private final HashMap<Integer, HashSet<BankTransaction>> transactionsByPayee = new HashMap<>();



    private Optional<BankAccount> fetchAccountForId(int accountId) {
        return new BankAccountsDAOProvider().getBankAccountsDAONoDB().fetchBankAccountForAccountId(accountId);
    }

    private Optional<BankTransaction> synchronizedCreateBankTransfer(BankAccount payer, BankAccount payee, Money amount, LocalDate transactionDate){

        Optional<BankTransaction> tr;
        synchronized (transactions) {
            int newId = transactions.size()+1;
            BankTransaction bankTransaction = new BankTransaction(newId, payer, payee, amount, transactionDate);

            if (transactions.add(bankTransaction)) {
                HashSet<BankTransaction> transactions =
                    transactionsByPayee.computeIfAbsent(payee.getAccountId(), s -> new HashSet<>());
                transactions.add(bankTransaction);
                tr = Optional.of(bankTransaction);
            }
            else
                tr = Optional.empty();
        }

        return tr;
    }

}

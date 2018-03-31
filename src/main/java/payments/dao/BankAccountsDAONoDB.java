package payments.dao;

import payments.model.BankAccount;
import payments.model.BankAccountHolder;
import payments.model.InternalBankAccount;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;

import java.util.*;

public class BankAccountsDAONoDB implements BankAccountsDAO {

    //Singleton

    private static class Helper{
        private static final BankAccountsDAONoDB INSTANCE = new BankAccountsDAONoDB();
    }

    static BankAccountsDAONoDB getInstance() {
        return BankAccountsDAONoDB.Helper.INSTANCE;
    }

    //Interface implementation

    @Override
    public Optional<BankAccount> setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode) {

        Optional<CurrencyUnit> currency = getCurrencyFromISO(currencyISOCode);

        return currency.flatMap(c-> getAccountHolderById(cliendId)
                .flatMap(h -> synchronizedCreateBankAccount(h, bic,iban, ukSortCode, ukAccountNumber, c)));
    }

    @Override
    public Optional<Set<BankAccount>> fetchClientBankAccounts(int clientId) {
        return Optional.ofNullable(accountsByClientID.get(clientId));
    }

    //

    private final TreeSet<BankAccount> accounts = new TreeSet<>();
    private final TreeMap<Integer, TreeSet<BankAccount>> accountsByClientID = new TreeMap<>();


    private Optional<CurrencyUnit> getCurrencyFromISO(String currencyISOCode) {
        CurrencyUnit currencyUnit;
        try {
            currencyUnit = CurrencyUnit.of(currencyISOCode);
        } catch (IllegalCurrencyException ex) {
            ex.printStackTrace();
            currencyUnit = null;
        }

        return Optional.ofNullable(currencyUnit);
    }

    private Optional<BankAccountHolder> getAccountHolderById(int clientId) {
        return new BankAccountHolderDAOProvider()
                .getBankAccountHolderDAONoDB()
                .getClientAccountHolder(clientId);
    }

    private Optional<BankAccount> synchronizedCreateBankAccount(BankAccountHolder holder, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency){
        BankAccount account;
        synchronized (accounts) {
            int newId = accounts.size() +1;
            account = InternalBankAccount.of(newId,bic,iban, ukSortCode, ukAccountNumber, currency, holder);
            if(!accounts.add(account))
                account = null;
            else {
                //update our index
                TreeSet<BankAccount> clientAccounts = accountsByClientID.computeIfAbsent(holder.getClientId(), k -> new TreeSet<>());
                clientAccounts.add(account);
            }
        }

        return Optional.ofNullable(account);
    }
}

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
    public Optional<HashSet<BankAccount>> fetchClientBankAccounts(int clientId) {
        return Optional.ofNullable(accountsByClientID.get(clientId));
    }


    private final HashSet<BankAccount> accounts = new HashSet<>();
    private final HashMap<Integer, HashSet<BankAccount>> accountsByClientID = new HashMap<>();


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
            int newId = accounts.size() +1; //to do - relace this with a simple counter
            account = InternalBankAccount.of(newId,bic,iban, ukSortCode, ukAccountNumber, currency, holder);

            //TODO should use a hashset on IBAN -> O(1)
            boolean duplicateIban = accounts.stream().anyMatch( x-> x.getIBAN().equalsIgnoreCase(iban));

            if(duplicateIban || !accounts.add(account))
                account = null;
            else {
                //update our index
                HashSet<BankAccount> clientAccounts = accountsByClientID.computeIfAbsent(holder.getClientId(), k -> new HashSet<>());
                clientAccounts.add(account);
            }
        }

        return Optional.ofNullable(account);
    }
}

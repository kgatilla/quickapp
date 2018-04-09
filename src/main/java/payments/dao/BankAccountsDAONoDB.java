package payments.dao;

import payments.model.BankAccount;
import payments.model.BankAccountHolder;
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

        return currency.flatMap(client-> getAccountHolderById(cliendId)
                .flatMap(h -> synchronizedCreateBankAccount(h, bic,iban, ukSortCode, ukAccountNumber, client)));
    }

    @Override
    public Optional<HashSet<BankAccount>> fetchClientBankAccounts(int clientId) {
        return Optional.ofNullable(accountsByClientID.get(clientId));
    }


    private final HashMap<String, BankAccount> accountsByIBAN = new HashMap<>();
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
        synchronized (accountsByIBAN) {
            int newId = accountsByIBAN.size() +1;
            account = BankAccount.of(newId,bic.toUpperCase(),iban.toUpperCase(), ukSortCode.toUpperCase(),
                    ukAccountNumber.toUpperCase(), currency, holder);

            if(!accountsByIBAN.containsKey(iban))
            {
                HashSet<BankAccount> clientAccounts = accountsByClientID.computeIfAbsent(holder.getClientId(), k -> new HashSet<>());
                clientAccounts.add(account);
                accountsByIBAN.put(account.getIBAN(), account);
            }
            else
                account = null;
        }

        return Optional.ofNullable(account);
    }
}

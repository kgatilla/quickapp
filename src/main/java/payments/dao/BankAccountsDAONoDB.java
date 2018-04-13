package payments.dao;

import org.joda.money.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payments.model.BankAccount;
import payments.model.BankAccountHolder;
import payments.util.MoneyBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class BankAccountsDAONoDB implements BankAccountsDAO {
    private final static Logger log = LoggerFactory.getLogger(BankAccountsDAONoDB.class);

    //Singleton
    private static class Helper{
        private static final BankAccountsDAONoDB INSTANCE = new BankAccountsDAONoDB();
    }

    static BankAccountsDAONoDB getInstance() {
        return BankAccountsDAONoDB.Helper.INSTANCE;
    }

    private BankAccountsDAONoDB(){}

    //Interface implementation

    @Override
    public Optional<BankAccount> setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode) {
        log.debug("setupNewClientBankAccount: clientId={}, bic={}, iban={}, ukSortCode={}, ukAccountNumber={}, currencyISOCode={}"
                , cliendId, bic, iban, ukSortCode, ukAccountNumber, currencyISOCode);

        Optional<CurrencyUnit> currency = MoneyBuilder.of(currencyISOCode);

        return currency.flatMap(client-> getAccountHolderById(cliendId)
                .flatMap(h -> synchronizedCreateBankAccount(h, bic,iban, ukSortCode, ukAccountNumber, client)));
    }

    @Override
    public Optional<HashSet<BankAccount>> fetchClientBankAccounts(int clientId) {
        log.debug("fetchClientBankAccounts:{}", clientId);

        return Optional.ofNullable(accountsByClientID.get(clientId));
    }

    @Override
    public Optional<BankAccount> fetchBankAccountForAccountId(int accountId) {
        log.debug("fetchBankAccountForAccountId:{}", accountId);

        return Optional.ofNullable(accountsByAccountId.get(accountId));
    }


    private final HashSet<BankAccount> accounts = new HashSet<>();
    private final HashMap<Integer, BankAccount> accountsByAccountId = new HashMap<>();
    private final HashMap<String, BankAccount> accountsByIBAN = new HashMap<>();
    private final HashMap<Integer, HashSet<BankAccount>> accountsByClientID = new HashMap<>();

    private Optional<BankAccountHolder> getAccountHolderById(int clientId) {
        return new BankAccountHolderDAOProvider()
                .getBankAccountHolderDAO(DAOType.NoDB)
                .getClientAccountHolder(clientId);
    }

    private Optional<BankAccount> synchronizedCreateBankAccount(BankAccountHolder holder, String bic, String iban, String ukSortCode, String ukAccountNumber, CurrencyUnit currency){
        BankAccount account;
        synchronized (accounts) {
            int newId = accounts.size() +1;
            account = BankAccount.of(newId,bic.toUpperCase(),iban.toUpperCase(), ukSortCode.toUpperCase(),
                    ukAccountNumber.toUpperCase(), currency, holder);

            if(!accountsByIBAN.containsKey(iban))
            {
                accounts.add(account);
                HashSet<BankAccount> clientAccounts = accountsByClientID.computeIfAbsent(holder.getClientId(), k -> new HashSet<>());
                clientAccounts.add(account);
                accountsByIBAN.put(account.getIBAN(), account);
                accountsByAccountId.put(account.getAccountId(), account);
            }
            else
                account = null;
        }

        return Optional.ofNullable(account);
    }
}

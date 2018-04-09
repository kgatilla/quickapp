package payments.dao;

import payments.model.BankAccount;

import java.util.HashSet;
import java.util.Optional;

public interface BankAccountsDAO {
    Optional<BankAccount> setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    Optional<HashSet<BankAccount>> fetchClientBankAccounts(int clientId);
    Optional<BankAccount> fetchBankAccountForAccountId(int accountId);
}

package dao;

import model.BankAccount;

import java.util.Optional;
import java.util.Set;

public interface BankAccountsDAO {
    Optional<BankAccount> setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode);
    Optional<Set<BankAccount>> fetchClientBankAccounts(int clientId);
}

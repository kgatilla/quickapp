package service;

import dao.BankAccountHolderDAOProvider;
import dao.BankAccountsDAOProvider;
import model.BankAccount;
import model.BankAccountHolder;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

public class RESTAccountsService implements AccountsService{
    @Override
    public void setupClientAccountHolder(String firstName, String lastName, String email) {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Optional<BankAccountHolder> accountHolder = dao.getBankAccountHolderDAONoDB().setupClientAccountHolder(firstName, lastName, email);
        //TODO return the accountHolder ID if created
    }

    @Override
    public void setupNewClientBankAccount(int cliendId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode) {
        BankAccountsDAOProvider dao = new BankAccountsDAOProvider();
        Optional<BankAccount> account = dao.getBankAccountsDAONoDB().setupNewClientBankAccount(cliendId, bic, iban, ukSortCode, ukAccountNumber, currencyISOCode);
        //TODO return account ID if created
    }

    @Override
    public void fetchClientBankAccounts(int clientId) {
        BankAccountsDAOProvider dao = new BankAccountsDAOProvider();
        Optional<Set<BankAccount>> accounts = dao.getBankAccountsDAONoDB().fetchClientBankAccounts(clientId);
        //TODO return the list of account ids if exists
    }

    @Override
    public void setupPayeeAccountHolder(int clientId, String payeeFirstName, String payeeLastName, String email) {

    }

    @Override
    public void fetchPayeeAccountHoldersForClient(int clientId) {

    }

    @Override
    public void setupClientPayeeAccount(int clientId, int payeeId, String bic, String iban, String ukSortCode, String ukAccountNumber, String currencyISOCode) {

    }

    @Override
    public void fetchPayeeAccountsForClient(int clientId) {

    }


}

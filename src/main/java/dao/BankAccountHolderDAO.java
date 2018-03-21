package dao;

import model.BankAccountHolder;

import java.util.Optional;

public interface BankAccountHolderDAO {
    Optional<BankAccountHolder> setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email);
    Optional<BankAccountHolder> getClientAccountHolder(int clientId);
}

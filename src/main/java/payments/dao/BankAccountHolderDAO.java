package payments.dao;

import payments.model.BankAccountHolder;

import java.util.Optional;
import java.util.Set;

public interface BankAccountHolderDAO {
    Optional<BankAccountHolder> setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email);
    Optional<BankAccountHolder> getClientAccountHolder(int clientId);
    Set<Integer> getAllClientAccountHolderIds();
}

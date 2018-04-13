package payments.dao;

import payments.model.BankAccountHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BankAccountHolderDAOJooq implements BankAccountHolderDAO {

    //Singleton
    private static class Helper{
        private static final BankAccountHolderDAOJooq INSTANCE = new BankAccountHolderDAOJooq();
    }

    static BankAccountHolderDAOJooq getInstance() {
        return Helper.INSTANCE;
    }

    private BankAccountHolderDAOJooq(){}

    @Override
    public Optional<BankAccountHolder> setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email) {
        return Optional.empty();
    }

    @Override
    public Optional<BankAccountHolder> getClientAccountHolder(int clientId) {
        return Optional.empty();
    }

    @Override
    public Set<Integer> getAllClientAccountHolderIds() {
        return new HashSet<>();
    }
}

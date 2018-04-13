package payments.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payments.model.BankAccountHolder;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Database free DAO for Bank Account Holders
 */
class BankAccountHolderDAONoDB implements BankAccountHolderDAO{

    private static Logger log = LoggerFactory.getLogger(BankAccountHolderDAONoDB.class);

     //Singleton
    private static class Helper{
        private static final BankAccountHolderDAONoDB INSTANCE = new BankAccountHolderDAONoDB();
    }

    static BankAccountHolderDAONoDB getInstance() {
        return Helper.INSTANCE;
    }

    private BankAccountHolderDAONoDB(){}

    //in lieu of a db
    private final HashMap<String, BankAccountHolder> clients = new HashMap<>();
    private final HashMap<Integer, BankAccountHolder> clientsByIdIndex = new HashMap<>();

    @Override
    public Optional<BankAccountHolder> setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email){

        log.debug("setupClientAccountHolder: payeeFirstName={}, payeeLastName={}, email={}", payeeFirstName, payeeLastName, email);
        BankAccountHolder newClient;

        synchronized (clients) {
            int id = clients.size() +1;
            newClient = new BankAccountHolder(id, payeeFirstName, payeeLastName, email);
            if (clients.containsKey (email)) {
                //cannot add new client. someone with same id or same email might already exist.
                newClient = null;
            }
            else {
                clients.put(email, newClient);
                clientsByIdIndex.put(id, newClient);
            }
        }

        return Optional.ofNullable(newClient);
    }

    @Override
    public Optional<BankAccountHolder> getClientAccountHolder(int clientId) {
        log.debug("getClientAccountHolder:{}", clientId);
        return Optional.ofNullable(clientsByIdIndex.get(clientId));
    }

    @Override
    public Set<Integer> getAllClientAccountHolderIds() {
        log.debug("getAllClientAccountHolderIds.");
        return clientsByIdIndex.keySet();
    }


}

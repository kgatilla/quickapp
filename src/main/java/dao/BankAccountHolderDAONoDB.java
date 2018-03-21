package dao;

import model.BankAccountHolder;
import model.BankClient;

import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Database free DAO for Bank Account Holders
 */
class BankAccountHolderDAONoDB implements BankAccountHolderDAO{

    //Singleton
    private static class Helper{
        private static final BankAccountHolderDAONoDB INSTANCE = new BankAccountHolderDAONoDB();
    }

    public static BankAccountHolderDAONoDB getInstance() {
        return Helper.INSTANCE;
    }

    //in lieu of a db
    private final TreeSet<BankAccountHolder> clients = new TreeSet<>();
    private final TreeMap<Integer, BankAccountHolder> clientsByIdIndex = new TreeMap<>();

    @Override
    public Optional<BankAccountHolder> setupClientAccountHolder(String payeeFirstName, String payeeLastName, String email){
        //TODO: check parameters are correct
        BankClient newClient;

        synchronized (clients) {
            int id = clients.size() +1;
            newClient = new BankClient(id, payeeFirstName, payeeLastName, email);
            if (!clients.add(newClient)) {
                //cannot add new client. someone with same id or same email might already exist.
                newClient = null;
            }
            else {
                clientsByIdIndex.put(id, newClient);
            }
        }

        return Optional.ofNullable(newClient);
    }

    @Override
    public Optional<BankAccountHolder> getClientAccountHolder(int clientId) {
        return Optional.ofNullable(clientsByIdIndex.get(clientId));
    }


}

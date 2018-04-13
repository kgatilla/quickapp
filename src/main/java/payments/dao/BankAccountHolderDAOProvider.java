package payments.dao;

public class BankAccountHolderDAOProvider {

    public BankAccountHolderDAO getBankAccountHolderDAO(DAOType type) {
        switch (type) {
            case NoDB:
                return BankAccountHolderDAONoDB.getInstance();
            case ORM:
                return BankAccountHolderDAOORM.getInstance();
            case JOOQ:
                return BankAccountHolderDAOJooq.getInstance();
        }

        //TODO: throw exception
        return null;
    }
}

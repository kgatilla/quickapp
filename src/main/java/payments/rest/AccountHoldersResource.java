package payments.rest;


import payments.dao.BankAccountHolderDAOProvider;
import payments.dao.DAOType;
import payments.model.BankAccountHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("holders")
public class AccountHoldersResource {

    //ex. http://localhost:8888/payments/api/v1.0/holders
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllAccountHolders() {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Set<Integer> ids= dao.getBankAccountHolderDAO(DAOType.NoDB).getAllClientAccountHolderIds();
        Set<String> idSet = ids.stream().map(String::valueOf).collect(Collectors.toSet());
        String res  = String.join(",", idSet);
        return  RESPONSE_TEXTS.RESPONSE_GET_ALL_ACCOUNT_HOLDERS + res;
    }

    //ex. http://localhost:8888/payments/api/v1.0/holders/12
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountHolder(@PathParam  ("id") int id) {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Optional<BankAccountHolder> accountHolder = dao.getBankAccountHolderDAO(DAOType.NoDB).getClientAccountHolder(id);
        return accountHolder
                .map(BankAccountHolder::getEmail)
                .orElse(RESPONSE_TEXTS.RESPONSE_ERROR_GET_ACCOUNTS_HOLDER + id);
    }


    //ex. http://localhost:8888/payments/api/v1.0/holders?firstName=Jane&lastName=Bond&email=jane.bond@mi6.org
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createAccountHolder(@QueryParam("firstName") String firstName,
                                      @QueryParam("lastName") String lastName,
                                      @QueryParam("email") String email) {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Optional<BankAccountHolder> accountHolder = dao.getBankAccountHolderDAO(DAOType.NoDB)
                .setupClientAccountHolder(firstName, lastName, email);

        return accountHolder
                .map(x-> Integer.toString(x.getClientId()))
                .orElse( RESPONSE_TEXTS.RESPONSE_ERROR_CREATE_ACCOUNT_HOLDER);
    }
}

package payments.rest;


import payments.dao.BankAccountHolderDAOProvider;
import payments.model.BankAccountHolder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("holders")
public class AccountHoldersResource {

    //http://localhost:8888/payments/api/v1.0/holders
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllAccountHolders() {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Set<Integer> ids= dao.getBankAccountHolderDAONoDB().getAllClientAccountHolderIds();
        Set<String> idSet = ids.stream().map(String::valueOf).collect(Collectors.toSet());
        String res  = String.join(",", idSet);
        return "Account holder ID's in the system:" + res;
    }

    //ex. http://localhost:8888/payments/api/v1.0/holders/12
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountHolder(@PathParam  ("id") int id) {
        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Optional<BankAccountHolder> accountHolder = dao.getBankAccountHolderDAONoDB().getClientAccountHolder(id);
        return accountHolder
                .map(BankAccountHolder::getEmail)
                .orElse("Could not find account holder with id:" + id);
    }


    //ex. http://localhost:8888/payments/api/v1.0/holders?firstName=Jane&lastName=Bond&email=jane.bond@mi6.org
    @POST
    @Produces(MediaType.TEXT_PLAIN) //todo take 3 parameters
    public String createAccountHolder(@QueryParam("firstName") String firstName,
                                      @QueryParam("lastName") String lastName,
                                      @QueryParam("email") String email) {

        BankAccountHolderDAOProvider dao = new BankAccountHolderDAOProvider();
        Optional<BankAccountHolder> accountHolder = dao.getBankAccountHolderDAONoDB().setupClientAccountHolder(firstName, lastName, email);

        return accountHolder
                .map(x-> Integer.toString(x.getClientId()))
                .orElse("Error creating new Account holder!");
    }
}

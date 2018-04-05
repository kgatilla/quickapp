package payments.rest;


import payments.dao.BankAccountsDAOProvider;
import payments.model.BankAccount;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("accounts")
public class BankAccountsResource {

    //ex. http://localhost:8888/payments/api/v1.0/accounts?accountHolderId=12
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountsForAccountHolder(@QueryParam  ("accountHolderId") int accountHolderId) {
        BankAccountsDAOProvider dao = new BankAccountsDAOProvider();
        Optional<HashSet<BankAccount>> accounts = dao.getBankAccountsDAONoDB().fetchClientBankAccounts(accountHolderId);

        String res = accounts.map(x-> x.stream()
                                        .map(a->Integer.toString(a.getAccountId()))
                                        .collect(Collectors.joining(",")))
                             .orElse("");

        if (res.length()==0)
            res = RESPONSE_TEXTS.RESPONSE_ERROR_GET_ACCOUNTS_FOR_HOLDER + accountHolderId;

        return res;
    }


    //ex. http://localhost:8888/payments/api/v1.0/accounts?
    // cliendId=11&bic=FRXXPP&iban=GB12300123&ukSortCode=123456&ukAccountNumber=11223344&currencyISOCode=GBP
    @POST
    @Produces(MediaType.TEXT_PLAIN) //todo take 3 parameters
    public String createAccountForAccountHolder( @QueryParam("cliendId") int cliendId,
                                                 @QueryParam("bic") String bic,
                                                 @QueryParam("iban") String iban,
                                                 @QueryParam("ukSortCode") String ukSortCode,
                                                 @QueryParam("ukAccountNumber") String ukAccountNumber,
                                                 @QueryParam("currencyISOCode") String currencyISOCode){

        BankAccountsDAOProvider dao = new BankAccountsDAOProvider();
        Optional<BankAccount> newAccount= dao.getBankAccountsDAONoDB().setupNewClientBankAccount(cliendId, bic, iban, ukSortCode, ukAccountNumber, currencyISOCode);

        return newAccount
                .map(a-> Integer.toString(a.getAccountId()))
                .orElse(RESPONSE_TEXTS.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER + cliendId);
    }
}

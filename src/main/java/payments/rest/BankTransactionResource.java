package payments.rest;


import payments.dao.BankTransactionDAOProvider;
import payments.model.BankTransaction;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Path("transactions")
public class BankTransactionResource {

    //ex. http://localhost:8888/payments/api/v1.0/transactions?
    // payerId=1&payeeId=2&amount=1200&currencyISOCode=GBP&transactionDate=20181226
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createBankTransaction( @QueryParam("payerId") int payerId,
                                         @QueryParam("payeeId") int payeeId,
                                         @QueryParam("amount") String amount,
                                         @QueryParam("currencyISOCode") String currencyISOCode,
                                         @QueryParam("transactionDate") String basicISOTransactionDate){

        Optional<String> res = Optional.empty();
        LocalDate transactionDate=null;

        //TO DO: implement improved parameter checks
        try{
            transactionDate = LocalDate.parse(basicISOTransactionDate, DateTimeFormatter.BASIC_ISO_DATE);
        }
        catch (DateTimeParseException ex) {
            res = Optional.of(RESPONSE_TEXTS.RESPONSE_ERROR_CREATE_NEW_TRANSACTION_DATE_FORMAT);
        }

        double dAmount=0.0;
        try {
            dAmount = Double.parseDouble(amount);
        }
        catch (NumberFormatException ex) {
            res = Optional.of(ex.getLocalizedMessage());
        }

        return res.orElse( setupNewTransaction(payerId,payeeId,dAmount,currencyISOCode,transactionDate)
                        .map(tr->Integer.toString(tr.getTransactionId()))
                        .orElse( RESPONSE_TEXTS.RESPONSE_ERROR_CREATE_NEW_TRANSACTION));

    }

    private Optional<BankTransaction> setupNewTransaction(int payerAccountId, int payeeAcountId, double transferAmount, String currencyISOCode, LocalDate transferDate) {
        BankTransactionDAOProvider dao = new BankTransactionDAOProvider();
        return dao.getBankTransationDAONoDB()
                .setupNewTransaction( payerAccountId,  payeeAcountId,  transferAmount, currencyISOCode, transferDate);

    }
}

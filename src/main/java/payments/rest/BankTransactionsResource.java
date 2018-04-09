package payments.rest;


import payments.dao.BankAccountsDAO;
import payments.dao.BankAccountsDAOProvider;
import payments.dao.BankTransactionDAOProvider;
import payments.model.BankTransaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;

@Path("transactions")
public class BankTransactionsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String summaryBetween(@QueryParam("bankAccountId") int bankAccountId,
                                 @QueryParam("startDate") String startDate,
                                 @QueryParam("endDate") String summaryEndDate) {

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate end = LocalDate.parse(summaryEndDate, DateTimeFormatter.BASIC_ISO_DATE);

        if (bankAccountIdExists(bankAccountId)) {
            BankTransactionDAOProvider dao = new BankTransactionDAOProvider();
            Set<BankTransaction> transactions = dao.getBankTransationDAONoDB().transactionSummary(bankAccountId, start, end);
            return buildTransactionSummaryResponse(transactions, bankAccountId);
        }
        else
            return RESPONSE_TEXTS.RESPONSE_ERROR_GET_ACCOUNT_FOR_ACCOUNT_ID + bankAccountId;
    }


    private boolean bankAccountIdExists(int bankAccountId) {
        BankAccountsDAO dao = new BankAccountsDAOProvider().getBankAccountsDAONoDB();
        return dao.fetchBankAccountForAccountId(bankAccountId).isPresent();
    }

    private String buildTransactionSummaryResponse(Set<BankTransaction> transactions, int bankAccountId) {

        class InOutFlow {
            InOutFlow(double in, double out) {
                this.inFlow = in;
                this.outFlow = out;
            }
            private double inFlow;
            private double outFlow;

            public double getInFlow() {
                return inFlow;
            }

            public double getOutFlow() {
                return outFlow;
            }

            public InOutFlow add(InOutFlow inOutFlow) {
                return new InOutFlow(this.getInFlow() + inOutFlow.getInFlow(), this.getOutFlow() + inOutFlow.getOutFlow());
            }
        }


        InOutFlow summary = transactions.stream()
                .reduce(new InOutFlow(0.0,0.0),
                        ( prev, transaction) ->
                                transaction.getPayeeAccount().getAccountId() == bankAccountId ?
                                        new InOutFlow(prev.getInFlow() + transaction.getTransferAmount().getAmount().doubleValue(), prev.getOutFlow()):
                                        new InOutFlow(prev.getInFlow(), prev.getOutFlow() + transaction.getTransferAmount().getAmount().doubleValue()),

                        InOutFlow::add);

        String respone = String.format(RESPONSE_TEXTS.RESPONSE_SUMMARY_TRANSACTIONS, summary.getInFlow(), summary.getOutFlow());
        return respone;
    }



    //ex. http://localhost:8888/payments/api/v1.0/transactions?
    // payerId=1&payeeId=2&amount=1200&currencyISOCode=GBP&transactionDate=20181226
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createBankTransaction( @QueryParam("payerAccountId") int payerAccountId,
                                         @QueryParam("payeeAccountId") int payeeAccountId,
                                         @QueryParam("amount") String amount,
                                         @QueryParam("currencyISOCode") String currencyISOCode,
                                         @QueryParam("transactionDate") String transactionDate){

        Optional<String> response = Optional.empty();
        LocalDate td=null;

        //---- TO DO: implement improved parameter checks
        try{
            td = LocalDate.parse(transactionDate, DateTimeFormatter.BASIC_ISO_DATE);
        }
        catch (DateTimeParseException ex) {
            response = Optional.of(RESPONSE_TEXTS.RESPONSE_ERROR_CREATE_NEW_TRANSACTION_DATE_FORMAT);
        }

        double dAmount=0.0;
        try {
            dAmount = Double.parseDouble(amount);
        }
        catch (NumberFormatException ex) {
            response = Optional.of(ex.getLocalizedMessage());
        }
        //-----

        return response.orElse( setupNewTransaction(payerAccountId,payeeAccountId,dAmount,currencyISOCode,td)
                        .map(tr->Integer.toString(tr.getTransactionId()))
                        .orElse( RESPONSE_TEXTS.RESPONSE_ERROR_CREATE_NEW_TRANSACTION));

    }

    private Optional<BankTransaction> setupNewTransaction(int payerAccountId, int payeeAcountId, double transferAmount, String currencyISOCode, LocalDate transferDate) {
        BankTransactionDAOProvider dao = new BankTransactionDAOProvider();
        return dao.getBankTransationDAONoDB()
                .setupNewTransaction( payerAccountId,  payeeAcountId,  transferAmount, currencyISOCode, transferDate);
    }


}

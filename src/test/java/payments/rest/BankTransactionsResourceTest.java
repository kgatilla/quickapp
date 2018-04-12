package payments.rest;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class BankTransactionsResourceTest {

    private static class HelperBankTransactionsResourceTest {

        private int buildAccountHolder(String firstName, String lastName, String email) {
            AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
            String res = accountHoldersResource.createAccountHolder(firstName, lastName, email);
            assertTrue("Account holder" + firstName + " " + lastName + " should be created:" + res, !res.contains("Error"));
            return Integer.parseInt(res);
        }

        private int[] buildAccountHolders() {
            int[] holderIds = new int[3];

            holderIds[0] = buildAccountHolder("James", "Dean", "jd@gmail.com");
            holderIds[1] = buildAccountHolder("Frank", "Sinatra", "fd@gmail.com");
            holderIds[2] = buildAccountHolder("Ella", "Fitzgerald", "ef@gmail.com");

            return holderIds;
        }

        private int buildBankAccount(int holderId) {

            BankAccountsResource bankAccountsResource = new BankAccountsResource();
            String newBankAccountId = bankAccountsResource
                    .createAccountForAccountHolder(holderId, "GBAABBXX", "GB00 0000 00000 " + holderId, "sort:" + holderId, "11111 " + holderId, "GBP");
            assertTrue("Bank Account should have been created." + newBankAccountId, !newBankAccountId.contains(RESPONSE_TEXTS.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));

            return Integer.parseInt(newBankAccountId);
        }

        private int[] buildBankAccounts(int[] holderIds) {
            return Arrays.stream(holderIds).map(this::buildBankAccount).toArray();
        }

        private void buildTransactions(int[] bankAccountIds) {
            BankTransactionsResource btr = new BankTransactionsResource();
            btr.createBankTransaction(bankAccountIds[0], bankAccountIds[1], "001.0", "GBP", "20180101" /*YYYYMMDD*/);
            btr.createBankTransaction(bankAccountIds[0], bankAccountIds[1], "10.00", "GBP", "20180102");
            btr.createBankTransaction(bankAccountIds[0], bankAccountIds[1], "100", "GBP", "20180103");

            btr.createBankTransaction(bankAccountIds[2], bankAccountIds[1], "1000", "GBP", "20180104");

            btr.createBankTransaction(bankAccountIds[1], bankAccountIds[0], "0.11", "GBP", "20180105" /*YYYYMMDD*/ );
        }
    }


    private static int[] accountIds;

    @BeforeClass
    public static void  createHoldersAndAccounts(){
        HelperBankTransactionsResourceTest h= new HelperBankTransactionsResourceTest();
        int[] holderIds = h.buildAccountHolders();
        accountIds = h.buildBankAccounts(holderIds);
    }


    @Test
    public void summaryBetween() {

        HelperBankTransactionsResourceTest h= new HelperBankTransactionsResourceTest();
        h.buildTransactions(accountIds);

        BankTransactionsResource btr = new BankTransactionsResource();
        String res = btr.summaryBetween(accountIds[1],"20171231", "20180106");

        assertTrue("Inflow should be: 1111.00 outflow should be 0.11!" +res, res.contains("1111.00") && res.contains("0.11"));
    }


    @Test
    public void createBankTransaction() {
        BankTransactionsResource btr = new BankTransactionsResource();
        String res= btr.createBankTransaction(accountIds[0], accountIds[2], "0.22", "GBP", "20180105" /*YYYYMMDD*/ );

        Optional<Integer> id;
        try {
            Integer i = Integer.parseInt(res);
            id = Optional.of(i);
        }
        catch (NumberFormatException ex) {
            id = Optional.empty();
        }

        assertTrue("Bank account transaction should have been created." + res, id.isPresent());
    }

}
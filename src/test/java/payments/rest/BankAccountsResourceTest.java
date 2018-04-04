package payments.rest;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankAccountsResourceTest {

    @Test
    public void getAccountsForAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("Ms. Katia", "Wolf", "Katia77@gmail.com");
        assertTrue("Account holder Ms. Katia Wolf should be created:" + res, !res.contains("Error"));

        int katiaId = Integer.parseInt(res);
        BankAccountsResource bankAccountsResource = new BankAccountsResource();
        res = bankAccountsResource.getAccountsForAccountHolder(katiaId);
        assertTrue("Ms. Katia Wolf should not yet have any bank accounts",
                res.contains(ResponseText.RESPONSE_ERROR_GET_ACCOUNTS_FOR_HOLDER));

        res = bankAccountsResource
                .createAccountForAccountHolder( katiaId, "GBAABBXX","GB11 1111 0000 1111 2222 33","112233","01020304", "GBP");
        assertTrue("Ms. Katia wold account should have been created." + res, !res.contains(ResponseText.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));

        res = bankAccountsResource
                .createAccountForAccountHolder( katiaId, "GBAABBZZ","GB22 2222 0000 1111 2222 33","112233","01020304", "GBP");
        assertTrue("Ms. Katia Wolf bank account should have been created." + res, !res.contains(ResponseText.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));

        res = bankAccountsResource.getAccountsForAccountHolder(katiaId);
        assertTrue("Ms. Katia Wolf should have two bank accounts", res.matches("([0-9].*),([0-9].*)"));
    }

    @Test
    public void createAccountForAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("Ms. Alice", "Cooper", "AliceCooper@gmail.com");
        assertTrue("Account holder Ms. Alice Cooper should be created:" + res, !res.contains("Error"));

        int id = Integer.parseInt(res);
        BankAccountsResource bankAccountsResource = new BankAccountsResource();
        res = bankAccountsResource.getAccountsForAccountHolder(id);
        assertTrue("Ms. Alice Cooper should not yet have any bank accounts",
                res.contains(ResponseText.RESPONSE_ERROR_GET_ACCOUNTS_FOR_HOLDER));

        res = bankAccountsResource
                .createAccountForAccountHolder( id, "GBAABBXX","GB00 1111 0000 1111 2222 33","002233","66020304", "GBP");
        assertTrue("Ms. Alice Cooper bank account should have been created." + res, !res.contains(ResponseText.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));
    }

    @Test
    public void createDuplicateAccountsForAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("Mr. James", "Lund", "jamesLund@gmail.com");
        assertTrue("Account holder Mr. James Lund should be created:" + res, !res.contains("Error"));

        int id = Integer.parseInt(res);
        BankAccountsResource bankAccountsResource = new BankAccountsResource();
        res = bankAccountsResource.getAccountsForAccountHolder(id);
        assertTrue("Mr. James Lund  should not yet have any bank accounts",
                res.contains(ResponseText.RESPONSE_ERROR_GET_ACCOUNTS_FOR_HOLDER));

        res = bankAccountsResource
                .createAccountForAccountHolder( id, "GBAABBXX","GB00 1111 0000 1111 2222 00","002233","66020304", "GBP");
        assertTrue("Mr. James Lund  bank account should have been created." + res, !res.contains(ResponseText.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));

        res = bankAccountsResource
                .createAccountForAccountHolder( id, "GBAABBXX","GB00 1111 0000 1111 2222 00","002233","66020304", "GBP");
        assertTrue("Ms. Alice Cooper duplicate bank account should not be created." + res, res.contains(ResponseText.RESPONSE_ERROR_NEW_ACCOUNT_FOR_HOLDER));


    }
}
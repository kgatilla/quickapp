package payments.rest;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountHoldersResourceTest {

    @Test
    public void getAllAccountHolders() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();

        String res = accountHoldersResource.getAllAccountHolders();
        assertTrue(res, res.contains(ResponseText.RESPONSE_GET_ALL_ACCOUNT_HOLDERS));

        res = accountHoldersResource.createAccountHolder("Mr. John", "Smith", "JohnSmith@gmail.com");
        assertTrue("Account holder Mr. John Smith should be created:" + res, !res.contains("Error"));

        String johnId=res;
        res = accountHoldersResource.getAllAccountHolders();
        assertTrue("There should be at least one account with id:" + johnId, res.endsWith(johnId));
    }

    @Test
    public void getAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("Ms. Janis", "Joplin", "Janis.Joplin@gmail.com");
        assertTrue("Account holder Ms. Janis Joplin should be created:" + res, !res.contains("Error"));

        int janisId = Integer.parseInt(res);
        res = accountHoldersResource.getAccountHolder(janisId);
        assertTrue("Account with id:" + janisId + " should be Ms. Janis Joplin.", res.contains("Janis.Joplin@gmail.com"));
    }

    @Test
    public void createAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("James", "Bond", "James.Bond@gmail.com");
        assertTrue("Account holder James Bond should be created:" + res, !res.contains("Error"));
    }

    @Test
    public void createDuplicateAccountHolder() {
        AccountHoldersResource accountHoldersResource = new AccountHoldersResource();
        String res = accountHoldersResource.createAccountHolder("Ms. Jane 1", "Bond", "Jane.Bond@gmail.com");
        assertTrue("Account holder Jane Bond 1 should be created:" + res, !res.contains("Error"));

        res = accountHoldersResource.createAccountHolder("Ms. Jane 1", "Bond", "Jane.Bond@gmail.com");
        assertTrue("Account holder Jane 2 Bond should NOT be created:" + res, res.contains("Error"));
    }
}
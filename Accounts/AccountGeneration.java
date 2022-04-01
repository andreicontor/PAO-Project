package Accounts;

public class AccountGeneration {

    private static int id = 0;

    public Account createAccount(String name, int customerId)
    {
        return new Account(name, customerId, id++);
    }

}
package Accounts;

public class AccountGeneration {

    private static int id = 0;

    public static void incrementUniqueId(int inc) {
        AccountGeneration.id += inc;
    }

    public Account createAccount(String name, int customerId)
    {
        return new Account(name, customerId, id++);
    }

}

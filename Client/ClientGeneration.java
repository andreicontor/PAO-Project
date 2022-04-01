package Client;


import java.util.Scanner;

public class ClientGeneration {
    private static int id = 0;

    public Client createClient(Scanner in)
    {
        return new Client(id++, in);
    }

}
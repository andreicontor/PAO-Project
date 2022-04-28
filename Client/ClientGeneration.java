package Client;


import java.text.ParseException;
import java.util.Scanner;

public class ClientGeneration {
    private static int id = 0;

    public static void incrementUniqueId(int inc) {
        ClientGeneration.id += inc;
    }

    public Client createClient(Scanner in) throws ParseException
    {
        return new Client(id++, in);
    }

}

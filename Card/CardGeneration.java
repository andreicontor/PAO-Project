package Card;

public class CardGeneration
{
    private static int id = 0;
    public Card addCard(String IBAN, String name)
    {
        return new Card(id++, IBAN, name);
    }

    public MasterCard addMasterCard(String IBAN, String name)
    {
        return new MasterCard(id++, IBAN, name);
    }

    public Visa addVisa(String IBAN, String name)
    {
        return new Visa(id++, IBAN, name);
    }

    public Maestro addMaestro(String IBAN, String name)
    {
        return new Maestro(id++, IBAN, name);
    }

}
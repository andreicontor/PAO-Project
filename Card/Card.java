package Card;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Card {
    private final int cardId;
    private final int CVV;
    private final Date expirationDate;
    private String number;
    private String name;
    private String IBAN;

    static private final Set<String> used = new HashSet<>();

    public Card(int cardId, String IBAN, String name)
    {
        this.cardId = cardId;
        this.IBAN = IBAN;
        this.name = name;
        this.number = this.generateNumber();

        while(used.contains(this.number))
            this.number = this.generateNumber();
        used.add(this.number);

        this.CVV = this.generateCVV();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 4);
        this.expirationDate = c.getTime();
    }
    private String generateNumber()
    {
        byte[] nr = new byte[16];
        new Random().nextBytes(nr);
        return new String(nr, StandardCharsets.UTF_8);
    }

    public int getCardId() {return cardId;}
    public int getCVV() {return CVV;}
    public Date getExpirationDate() {return expirationDate;}
    public String getNumber() {return number;}
    public String getName() {return name;}
    public String getIBAN() {return IBAN;}

    private int generateCVV()
    {
        var cvv = new Random();
        var c =  cvv.nextInt(999);
        if(c < 100)
            c += 100;
        return c;
    }

}

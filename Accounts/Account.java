package Accounts;

import Card.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    protected String IBAN;
    protected double amount;
    protected String name;
    protected int clientId;

    protected List<Card> cards = new ArrayList<>();

    private final CardGeneration cardGeneration = new CardGeneration();

    public Account(String IBAN, double amount, String name, int clientId)
    {
        this.IBAN = IBAN;
        this.amount = amount;
        this.name = name;
        this.clientId = clientId;
    }

    public Account(String name, int clientId, int uniqueId)
    {
        this.IBAN = this.generateIBAN(uniqueId);
        this.amount = 0;
        this.name = name;
        this.clientId = clientId;
    }

    public Account(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.amount = in.getDouble("amount");
        this.name = in.getString("name");
        this.clientId = in.getInt("customerId");
    }


    public List<Transaction> filterTransactions(List<Transaction> allTransactions)
    {
        List<Transaction> transactions = new ArrayList<>();
        for(var transaction: allTransactions)
            if(transaction.getFromIBAN().equals(this.IBAN))
                transactions.add(transaction);
        return transactions;
    }

    public String getIBAN() {return IBAN;}
    public double getAmount() {return amount;}
    public String getName() {return name;}
    public int getClientId() {return clientId;}

    public void addCard(String name)
    {
        Card newCard = cardGeneration.addCard(this.IBAN, name);
        cards.add(newCard);
    }
    public int compare(Transaction transaction1, Transaction transaction2){
        return transaction1.getTransactionDate().compareTo(transaction2.getTransactionDate());
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    private String generateIBAN(int uniqueId)
    {
        List<String> banks = Arrays.asList("BCR", "BTR", "BRD", "CEC", "BRZ");
        Random rand = new Random();
        String random_bank = banks.get(rand.nextInt((banks.size())));

        List<String> numbers = Arrays.asList("01", "08", "21", "66", "45", "09", "72");
        Random rand1 = new Random();
        String random_number = numbers.get(rand1.nextInt((numbers.size())));

        return "RO" + random_number + random_bank + uniqueId;
    }

    public String toString()
    {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", clientId=" + clientId +
                '}';
    }

    public String toCSV()
    {
        return IBAN + "," + amount + "," + name + "," + clientId;
    }

}

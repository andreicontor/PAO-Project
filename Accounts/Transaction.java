package Accounts;

import java.util.Date;

public class Transaction {
    private final String fromIBAN;
    private final String toIBAN;
    private final double amount;
    private final String details;
    private final Date transactionDate;

    public Transaction (String fromIBAN, String toIBAN, double amount, String details)
    {
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.details = details;
        this.transactionDate = new Date();
    }

    public String getFromIBAN() {return fromIBAN;}
    public String getToIBAN() {return toIBAN;}
    public double getAmount() {return amount;}
    public String getDetails() {return details;}
    public Date getTransactionDate() {return transactionDate;}
}
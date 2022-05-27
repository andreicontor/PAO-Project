package Client;

import Accounts.Account;
import Accounts.Transaction;
import Card.CardGeneration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Client {
    private final int clientId;
    private String firstName, lastName, CNP;
    private String email, phone;
    private Address address;

    public Client(int clientId, String firstName, String lastName, String CNP, String email, String phone, Address address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client(int clientId, Scanner in) throws ParseException
    {
        this.clientId = clientId;
        this.read(in);
    }

    public Client(int customerId, ResultSet in) throws SQLException {
        this.clientId = customerId;
        this.read(in);
    }

    public void read(ResultSet in) throws SQLException {
        this.firstName = in.getString("firstName");
        this.lastName = in.getString("lastName");
        this.CNP = in.getString("CNP");
        this.email = in.getString("email");
        this.phone = in.getString("phone");
        this.address = new Address(in);
    }

    public void read(Scanner in) throws ParseException
    {
        System.out.println("First name: ");
        this.firstName = in.nextLine();
        System.out.println("Last name: ");
        this.lastName = in.nextLine();
        System.out.println("CNP: ");
        this.CNP = in.nextLine();
        System.out.println("Email: ");
        this.email = in.nextLine();
        System.out.println("Phone: ");
        this.phone = in.nextLine();
        System.out.println("Address: ");
        this.address = new Address(in);
    }

    public List<Account> filterAccounts(List<Account> allAccounts){
        var accounts = new ArrayList<Account>();
        for(var account: allAccounts)
            if(account.getClientId() == this.getClientId())
                accounts.add(account);
        return accounts;
    }

    public List<Transaction> filterTransactions(List<Account> allAccounts, List<Transaction> allTransactions){
        var transactions = new ArrayList<Transaction>();
        var accounts = this.filterAccounts(allAccounts);
        for(var account: accounts)
            transactions.addAll(account.filterTransactions(allTransactions));
        return transactions;
    }

    public int getClientId() {
        return clientId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getCNP() {
        return CNP;
    }
    public Address getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public void setCNP(String CNP)
    {
        this.CNP = CNP;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public void setAddress(Address address)
    {
        this.address = address;

    }

    public String toString()
    {
        return "{" +
                "customerId=" + clientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address.toString() +
                '}';
    }

    public String toCSV()
    {
        return clientId +
                "," + firstName +
                "," + lastName +
                "," + CNP +
                "," + email +
                "," + phone +
                "," + address.toCSV();
    }
}

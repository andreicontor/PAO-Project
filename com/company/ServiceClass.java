package com.company;

import Accounts.*;
import Card.*;
import Client.*;

import java.util.*;
import java.text.ParseException;

import static com.company.Main.availableCommands;
import static com.company.Main.commandsDescriptions;

public class ServiceClass {

    private List<Client> clients = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    private final Map<String, Account> accountsMap = new HashMap<>();
    private final ClientGeneration clientGeneration = new ClientGeneration();
    private final AccountGeneration accountGeneration = new AccountGeneration();

    private ClientDatabase customerDatabase = null;
    private TransactionDatabase transactionDatabase = null;
    private AccountDatabase accountDatabase = null;



    public List<Client> getClients() {return clients;}
    public List<Account> getAccounts() {return accounts;}
    public List<Transaction> getTransactions() {return transactions;}


    public void setClients(List<Client> clients)
    {
        this.clients = clients;
    }
    public void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }
    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public ServiceClass(ClientDatabase customerDatabase, TransactionDatabase transactionDatabase, AccountDatabase accountDatabase) {
        this.customerDatabase = customerDatabase;
        this.transactionDatabase = transactionDatabase;
        this.accountDatabase = accountDatabase;

        this.clients = customerDatabase.read();
        this.transactions = transactionDatabase.read();
        this.accounts = accountDatabase.read();

        this.linkAccounts();
    }

    private Client getClientFromInput(Scanner in)
    {
        if(this.clients.size()==1)
            return clients.get(0);
        System.out.println("Client id [0-"+(this.clients.size()-1)+"]: ");
        int clientId = Integer.parseInt(in.nextLine());
        return clients.get(clientId);

    }

    public void printAllCommands()
    {
        for(int i=0;i<availableCommands.size();++i)
            System.out.println((i+1) + ". " + commandsDescriptions.get(i) + " (" + availableCommands.get(i) + ")");
    }

    public void getClient(Scanner in)
    {
        Client client  = this.getClientFromInput(in);
        System.out.println(client.toString());
    }

    public void linkAccounts()
    {
        for(var account: this.accounts)
            this.accountsMap.put(account.getIBAN(), account);
    }

    public void createClient(Scanner in) throws ParseException {
        Client newCustomer = clientGeneration.createClient(in);
        this.clients.add(newCustomer);
        var newAccount = accountGeneration.createAccount(newCustomer.getFirstName() + " " + newCustomer.getLastName(), newCustomer.getClientId());
        this.accounts.add(newAccount);
        if(this.customerDatabase!=null)
            this.customerDatabase.create(newCustomer);
        if(this.accountDatabase!=null)
            this.accountDatabase.create(newAccount);
        System.out.println("Customer created");
    }

    private Account getAccountFromInput(Scanner in, Client client)
    {
        List<Account> clientsAccounts = client.filterAccounts(this.accounts);
        System.out.println("client accounts: " + clientsAccounts);
        System.out.println("Choose IBAN: ");
        var IBAN = in.nextLine();
        var account = accountsMap.get(IBAN);
        return account;
    }

    public void getClientAmount(Scanner in)
    {
        var client = this.getClientFromInput(in);
        var clientAccounts = client.filterAccounts(this.accounts);
        double totalAmount = 0;
        for(var account: clientAccounts)
            totalAmount += account.getAmount();
        System.out.println(client.getFirstName() + " " + client.getLastName() + " has a total amount of: " + totalAmount + " lei in his accounts.");
    }

    public void getClientAccounts(Scanner in)
    {
        var client = this.getClientFromInput(in);
        List<Account> clientsAccounts = client.filterAccounts(this.accounts);
        System.out.println(clientsAccounts.toString());
    }

    public void createClientAccount(Scanner in) throws Exception {
        var customer = this.getClientFromInput(in);
        System.out.println("Account name: ");
        String name = in.nextLine();
        Account newAccount = this.accountGeneration.createAccount(name, customer.getClientId());
        accounts.add(newAccount);
        accountsMap.put(newAccount.getIBAN(), newAccount);
        if(this.accountDatabase!=null)
            this.accountDatabase.create(newAccount);
        System.out.println("Account created");
    }

    public void createClientCard(Scanner in)
    {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println("Card Holder name: ");
        var name = in.nextLine();
        account.addCard(name);
    }

    public void loadClientAccount(Scanner in)
    {
        var client = this.getClientFromInput(in);
        System.out.println("What amount goes into your account?: ");
        int amount = Integer.parseInt(in.nextLine());
        var clientAccounts = client.filterAccounts(this.accounts);
        clientAccounts.get(0).setAmount(amount);
        System.out.println("The account has been loaded!");
    }
    public void createTransaction(Scanner in) throws Exception {
        System.out.println("From account (IBAN): ");
        var IBAN1 = in.nextLine();
        System.out.println("To account (IBAN): ");
        var IBAN2 = in.nextLine();
        System.out.println("Amount: ");
        int amount = in.nextInt();
        System.out.println("Description: ");
        var description = in.nextLine();

        Account account1 = null, account2 = null;

        if(accountsMap.containsKey(IBAN1))
            account1 = accountsMap.get(IBAN1);
        if(accountsMap.containsKey(IBAN2))
            account2 = accountsMap.get(IBAN2);

        if(IBAN1.equals(IBAN2))
            throw new Exception("Cannot send transaction to same account");
        if(account1==null || account2==null)
            throw new Exception("Cannot find IBAN numbers!");
        if(account1.getAmount() < amount)
            throw new Exception("Insufficient founds!");

        account1.setAmount(account1.getAmount() - amount);
        account2.setAmount(account2.getAmount() + amount);

        var newTransaction = new Transaction(IBAN1, IBAN2, amount, description);
        this.transactions.add(newTransaction);
        if(this.transactionDatabase!=null)
            this.transactionDatabase.create(newTransaction);
        if(this.accountDatabase!=null){
            this.accountDatabase.update(account1);
            this.accountDatabase.update(account2);
        }
        System.out.println("Transaction finished");
    }


    public void closeAccount(Scanner in) throws Exception {
        var customer = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, customer);

        if(customer.filterAccounts(this.accounts).size()<=1)
            throw new Exception("There has to be at least one bank account associated with the user!");
        if(account.getAmount()!=0)
            throw new Exception("The account savings are not empty!");
        this.accountsMap.remove(account.getIBAN());
        this.accounts.remove(account);
        if(this.accountDatabase!=null)
            this.accountDatabase.delete(account);
        System.out.println("Account closed!");
    }

    public void getClientAccount(Scanner in)
    {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println(account.toString());
    }

    public void getClientTransactions(Scanner in)
    {
        var client = this.getClientFromInput(in);
        System.out.println("Show all transactions? (y/n)");
        String showAll = in.nextLine();
        if(showAll.equals("y"))
        {
            System.out.println(client.filterTransactions(accounts, transactions));
        }
        System.out.println();
    }
}

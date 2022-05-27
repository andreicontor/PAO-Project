/*package com.company;

import Accounts.Account;
import Accounts.Transaction;
import Card.Visa;
import Client.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static List<String> availableCommands = Arrays.asList("create_client", "get_client",
            "get_client_amount", "get_client_accounts", "load_client_account",
            "create_transaction", "create_client_account", "close_client_account",
            "get_client_transactions", "help", "end");
    static List<String> commandsDescriptions = Arrays.asList("creare cont client", "afis detalii client", "obtinere sold client",
            "Preluare conturi client", "Încărcare cont client", "Creeare tranzacție", "Creare cont client",
            "Închidere cont client", "Preluare transacții client",
            "Afișează comenzi", "Finalizare");



    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        boolean end = false;


        ServiceClass serviceClass = new ServiceClass();
        while (!end)
        {
            System.out.println("Insert command: (help - see commands)");
            String command = in.nextLine().toLowerCase(Locale.ROOT);
            try {
                if(command.equals("create_client"))
                    serviceClass.createClient(in);
                else
                if(command.equals("get_client"))
                    serviceClass.getClient(in);
                else
                if(command.equals("get_client_amount"))
                    serviceClass.getClientAmount(in);
                else
                if(command.equals("get_client_accounts"))
                    serviceClass.getClientAccounts(in);
                else
                if(command.equals("get_client_account"))
                    serviceClass.getClientAccount(in);
                else
                if(command.equals("load_client_account"))
                    serviceClass.loadClientAccount(in);
                else
                if(command.equals("create_transaction"))
                    serviceClass.createTransaction(in);
                else
                if(command.equals("create_client_account"))
                    serviceClass.createClientAccount(in);
                else
                if(command.equals("close_client_account"))
                    serviceClass.closeAccount(in);
                else
                if(command.equals("get_client_transactions"))
                    serviceClass.getClientTransactions(in);
                else
                if(command.equals("help"))
                    serviceClass.printAllCommands();
                else
                if(command.equals("end"))
                    end = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
*/




/*package com.company;

import Accounts.Account;
import Accounts.Account_Singleton;
import Accounts.Transaction;
import Accounts.Transaction_Singleton;
import Card.Visa;
import Client.Client;
import Client.*;

import Client.Client_Singleton;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static List<String> availableCommands = Arrays.asList("create_client", "create_client_card", "get_client",
            "get_client_amount", "get_client_accounts", "load_client_account",
            "create_transaction", "create_client_account", "close_client_account",
            "get_client_transactions", "help", "end");
    static List<String> commandsDescriptions = Arrays.asList("creare cont client", "creare card client", "afis detalii client", "obtinere sold client",
            "Preluare conturi client", "Încărcare cont client", "Creeare tranzacție", "Creare cont client",
            "Închidere cont client", "Preluare transacții client",
            "Afișează comenzi", "Finalizare");


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        ServiceClass mainService = new ServiceClass();
        Audit auditService = new Audit();
        Client_Singleton.getInstance().loadFromCSV();
        Account_Singleton.getInstance().loadFromCSV();
        Transaction_Singleton.getInstance().loadFromCSV();
        mainService.setClients(Client_Singleton.getInstance().getCustomers());
        mainService.setAccounts(Account_Singleton.getInstance().getAccounts());
        mainService.setTransactions(Transaction_Singleton.getInstance().getTransactions());
        mainService.linkAccounts();
        while (!end) {
            System.out.println("Insert command: (help - see commands)");
            String command = in.nextLine().toLowerCase(Locale.ROOT);
            try {
                switch (command) {
                    case "create_client" -> mainService.createClient(in);
                    case "create_client_card" -> mainService.createClientCard(in);
                    case "get_client" -> mainService.getClient(in);
                    case "get_client_amount" -> mainService.getClientAmount(in);
                    case "get_client_accounts" -> mainService.getClientAccounts(in);
                    case "get_client_account" -> mainService.getClientAccount(in);
                    case "load_client_account" -> mainService.loadClientAccount(in);
                    case "create_transaction" -> mainService.createTransaction(in);
                    case "create_client_account" -> mainService.createClientAccount(in);
                    case "close_client_account" -> mainService.closeAccount(in);
                    case "get_client_transactions" -> mainService.getClientTransactions(in);
                    case "end" -> end = true;
                }
                if (availableCommands.contains(command))
                    auditService.logAction(command);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        Client_Singleton.getInstance().setCustomers(mainService.getClients());
        Account_Singleton.getInstance().setAccounts(mainService.getAccounts());
        Transaction_Singleton.getInstance().setTransactions(mainService.getTransactions());
        Client_Singleton.getInstance().dumpToCSV();
        Account_Singleton.getInstance().dumpToCSV();
        Transaction_Singleton.getInstance().dumpToCSV();
    }
}*/

package com.company;

import Client.ClientDatabase;
import Client.Client_Singleton;
import Accounts.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {

    static List<String> availableCommands = Arrays.asList("create_customer", "create_customer_card", "get_customer", "get_customer_amount", "get_customer_accounts", "load_customer_account", "create_transaction", "create_customer_account", "close_customer_account", "get_customer_transactions", "help", "end");
    static List<String> commandsDescriptions = Arrays.asList("Creează cont client", "Creează card client", "Afișare detalii client", "Preluare sold client", "Preluare conturi client", "Încărcare cont client", "Creeare tranzacție", "Creare cont client", "Închidere cont client", "Preluare transacții client", "Afișează comenzi", "Finalizare");

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/proiectpao"; /////nu este facuta baza de date
            String user = "root";
            String password = "Parola";  ////nu este facuta baza de date

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    private static void printAllCommands() {
        for (int i = 0; i < availableCommands.size(); ++i)
            System.out.println((i + 1) + ". " + commandsDescriptions.get(i) + " (" + availableCommands.get(i) + ")");
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        var connection = Main.getConnection();

        var customerDatabase = new ClientDatabase(connection);
        var transactionDatabase = new TransactionDatabase(connection);
        var accountDatabase = new AccountDatabase(connection);

        ServiceClass mainService = new ServiceClass(customerDatabase, transactionDatabase, accountDatabase);
        Audit auditService = new Audit();

        while (!end) {
            System.out.println("Insert command: (help - see commands)");
            String command = in.nextLine().toLowerCase(Locale.ROOT);
            try {
                switch (command) {
                    case "create_customer" -> mainService.createClient(in);
                    case "create_customer_card" -> mainService.createClientCard(in);
                    case "get_customer" -> mainService.getClient(in);
                    case "get_customer_amount" -> mainService.getClientAmount(in);
                    case "get_customer_accounts" -> mainService.getClientAccounts(in);
                    case "get_customer_account" -> mainService.getClientAccount(in);
                    case "load_customer_account" -> mainService.loadClientAccount(in);
                    case "create_transaction" -> mainService.createTransaction(in);
                    case "create_customer_account" -> mainService.createClientAccount(in);
                    case "close_customer_account" -> mainService.closeAccount(in);
                    case "get_customer_transactions" -> mainService.getClientTransactions(in);
                    case "help" -> Main.printAllCommands();
                    case "end" -> end = true;
                }
                if (availableCommands.contains(command))
                    auditService.logAction(command);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        try {
            assert connection != null;
            connection.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}

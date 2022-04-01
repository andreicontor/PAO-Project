package com.company;

import Accounts.Account;
import Accounts.Transaction;
import Card.Visa;
import Client.Client;

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
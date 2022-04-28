package Accounts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Account_Singleton {

    private static Account_Singleton single_instance = null;

    private List<Account> accounts = new ArrayList<Account>();

    public static Account_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Account_Singleton();
        return single_instance;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;

            while((line = in.readLine()) != null ) {
                String[] fields = line.replaceAll(" ", "").split(",");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved accounts!");
        }

        return columns;
    }

    public void loadFromCSV() {
        var columns = Account_Singleton.getCSVColumns("data/accounts.csv");
        for(var fields : columns){
            var newAccount = new Account(
                    fields[0],
                    Double.parseDouble(fields[1]),
                    fields[2],
                    Integer.parseInt(fields[3])
            );
            accounts.add(newAccount);
        }
        AccountGeneration.incrementUniqueId(columns.size());
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("data/accounts.csv");
            for(var account : this.accounts){
                writer.write(account.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}



package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Client_Singleton {

    private static Client_Singleton single_instance = null;

    final private ClientGeneration clientGeneration = new ClientGeneration();
    private List<Client> customers = new ArrayList<Client>();

    public static Client_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Client_Singleton();
        return single_instance;
    }

    public void setCustomers(List<Client> customers) {
        this.customers = customers;
    }

    public List<Client> getCustomers() {
        return customers;
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
            System.out.println("No saved customers!");
        }

        return columns;
    }

    public void loadFromCSV()
    {
            var columns = Client_Singleton.getCSVColumns("data/customers.csv");
            for (var fields : columns) {
                var newCustomer = new Client(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[5],
                        new Address(fields[6], fields[7], fields[8], Integer.parseInt(fields[9]))
                );
                customers.add(newCustomer);
            }
            ClientGeneration.incrementUniqueId(columns.size());
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("data/customers.csv");
            for(var customer : this.customers){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }


}
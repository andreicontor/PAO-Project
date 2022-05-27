package Client;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientDatabase{
    Connection connection;

    ClientGeneration customerFactory = new ClientGeneration();

    public ClientDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Client> read(){
        List<Client> customers = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Customers");
            while(result.next()) {
                Client current = customerFactory.createClient(result);
                customers.add(current);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return customers;
    }

    public void update(Client newCustomer){
        try{
            String query = "UPDATE Customers SET firstName = ?, lastName = ?, CNP = ?, email = ?, phone = ?, street = ?, city = ?, county = ?, postalCode = ? WHERE customerId = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, newCustomer.getFirstName());
            preparedStmt.setString(2, newCustomer.getLastName());
            preparedStmt.setString(3, newCustomer.getCNP());
            preparedStmt.setString(4, newCustomer.getEmail());
            preparedStmt.setString(5, newCustomer.getPhone());
            preparedStmt.setString(6, newCustomer.getAddress().getStreet());
            preparedStmt.setString(7, newCustomer.getAddress().getCity());
            preparedStmt.setString(8, newCustomer.getAddress().getCounty());
            preparedStmt.setInt(9, newCustomer.getAddress().getZIPCode());
            preparedStmt.setInt(10, newCustomer.getClientId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void create(Client customer){
        try{
            String query = "INSERT INTO Customers (customerId, firstName, lastName, CNP, email, phone, street, city, county, postalCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, customer.getClientId());
            preparedStmt.setString(2, customer.getFirstName());
            preparedStmt.setString(3, customer.getLastName());
            preparedStmt.setString(4, customer.getCNP());
            preparedStmt.setString(5, customer.getEmail());
            preparedStmt.setString(6, customer.getPhone());
            preparedStmt.setString(7, customer.getAddress().getStreet());
            preparedStmt.setString(8, customer.getAddress().getCity());
            preparedStmt.setString(9, customer.getAddress().getCounty());
            preparedStmt.setInt(10, customer.getAddress().getZIPCode());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void delete(Client customer){
        try{
            String query = "DELETE FROM Customers WHERE customerId = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, customer.getClientId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
package Client;

import java.util.Scanner;

public class Address {
    private String street, city, county;
    private int ZIPCode;

    public Address(String street, String city, String county, int ZIPCode)
    {
        this.street = street;
        this.city = city;
        this.county = county;
        this.ZIPCode = ZIPCode;
    }
    public Address(Scanner in)
    {
        this.read(in);
    }
    public void read(Scanner in)
    {
        System.out.println("Street: ");
        this.street = in.nextLine();
        System.out.println("City: ");
        this.city = in.nextLine();
        System.out.println("County: ");
        this.county = in.nextLine();
        System.out.println("Postal code: ");
        this.ZIPCode = Integer.parseInt(in.nextLine());
    }

    public String getStreet() {return street;}
    public int getZIPCode() {return ZIPCode;}
    public String getCity() {return city;}
    public String getCounty() {return county;}

    public void setStreet(String street)
    {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setCounty(String county)
    {
        this.county = county;
    }
    public void setZIPCode(int ZIPCode)
    {
        this.ZIPCode = ZIPCode;
    }


}
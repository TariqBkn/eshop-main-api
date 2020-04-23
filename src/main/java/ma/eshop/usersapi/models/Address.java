package ma.eshop.usersapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private int id;
    private String city;
    private String streetName;
    private String number;

    public Address(String city, String streetName, String number){
        this.city=city;
        this.streetName=streetName;
        this.number=number;
    }

    public Address(){

    }

    public String getCity() {
        return city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

 }

package ma.eshop.usersapi.models;

 import javax.persistence.Entity;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.validation.constraints.Email;
@Entity
public class User {

    @Id
    private int id;

    private String firstName;

    private String lastName;

    @OneToOne
    private Address address;

    @Email
    private String email;

    protected User(){
    }

    public User(String firstName, String lastName, String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package ma.eshop.usersapi.models;

 import org.hibernate.annotations.Cascade;

 import javax.persistence.*;
 import javax.validation.constraints.Email;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String firstName;

    private String lastName;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Email
    private String email;

    private boolean accountNonLocked;

     private Role role;

    protected User(){
    }

    public User(User user) {
        this.password=user.getPassword();
        this.email=user.getEmail();
        this.lastName=user.getLastName();
        this.firstName=user.getFirstName();
        this.accountNonLocked=user.isAccountNonLocked();
        this.role=user.getRole();
        this.id=user.getId();
    }

    public User(String firstName, String lastName, String email, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.role=Role.USER;
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
        this.email = email.toLowerCase();
    }

    protected boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Role getRole() {
        return role;
    }

    public int getId(){
        return id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

package ma.eshop.usersapi.models;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private int id;
    private final String firstName;
    private final String lastName;
    private final boolean accountNonLocked;
    private final String role;
    public JwtResponse(String jwtToken, User user) {
        this.jwtToken = jwtToken;
        this.id=id;
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.accountNonLocked=user.isAccountNonLocked();
        this.role=user.getRole().toString();
    }
    public String getToken() {
        return this.jwtToken;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public String getRole() {
        return role;
    }
}

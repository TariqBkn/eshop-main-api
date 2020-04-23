package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.JwtAuthenticationRequest;
import ma.eshop.usersapi.models.JwtResponse;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.services.JwtUtilService;
import ma.eshop.usersapi.services.MyUserDetailsService;
import ma.eshop.usersapi.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private MyUserDetailsService userDetailsService;

    @Inject
    private JwtUtilService jwtUtilService;

    @Inject
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {
        try {
            authenticate(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtAuthenticationRequest.getUsername());
        final String token = jwtUtilService.generateToken(userDetails);

        Optional<User> user = usersService.GetByEmail(jwtAuthenticationRequest.getUsername());
        if(user.isPresent()) {
            User foundUser = user.get();
            return ResponseEntity.ok(new JwtResponse(token, foundUser));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/signOn")
    public void signOn(@RequestBody User user ){
        usersService.createUser(user);
    }

    @GetMapping("/{email}")
    public User getUserData(@PathVariable String email){
        return null;
    }


    @PatchMapping("/admin/block/{email}")
        public void blockUser(@PathVariable String email){
    }

    @PatchMapping("/admin/unblock/{email}")
        public void unblockUser(@PathVariable String email){
    }
}

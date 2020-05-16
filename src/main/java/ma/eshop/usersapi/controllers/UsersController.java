package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.JwtAuthenticationRequest;
import ma.eshop.usersapi.models.JwtResponse;
import ma.eshop.usersapi.models.Product;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.services.JwtUtilService;
import ma.eshop.usersapi.services.MyUserDetailsService;
import ma.eshop.usersapi.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
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

    @PostMapping("/signup")
    public ResponseEntity signOn(@RequestBody User user ) throws URISyntaxException {
        if(usersService.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email_linked_to_an_other_account");
        }
        usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }
    @GetMapping("/me")
    User getConnectedUser(@AuthenticationPrincipal User connectedUser){
        return usersService.findById(connectedUser.getId());
    }

    @PatchMapping("/me")
    void updateUserData(@RequestBody User user){
        usersService.update(user);
    }

    @GetMapping("/{page}")
    public Page<User> getUsers(@RequestParam(defaultValue="0") int page){
        return usersService.findAll(PageRequest.of(page, 30));
    }

    @PatchMapping("/status/alter")
    public void alterAccountStatusOfUserWithId(@RequestBody int userId){
        usersService.alterAccountStatusOfUserWithId(userId);
    }

    @GetMapping("/search/{keyword}/pages/{page}")
    Page<User> search(@PathVariable String keyword, @PathVariable int page){
        return usersService.search(PageRequest.of(page, 30), keyword.toLowerCase());
    }

}

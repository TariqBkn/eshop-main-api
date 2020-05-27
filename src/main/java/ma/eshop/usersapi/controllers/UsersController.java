package ma.eshop.usersapi.controllers;

import ma.eshop.usersapi.models.*;
import ma.eshop.usersapi.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Inject
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {
        try {
            usersService.authenticate(jwtAuthenticationRequest);
            return usersService.getJwtResponseResponseEntity(jwtAuthenticationRequest);
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/signup")
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/blocked/count")
    int getNumberOfBlockedUser(){
        return usersService.blockedUsersNumber();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    long count(){
        return usersService.count();
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id){
        usersService.deleteById(id);
    }

}

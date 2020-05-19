package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.JwtAuthenticationRequest;
import ma.eshop.usersapi.models.JwtResponse;
import ma.eshop.usersapi.models.Role;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UsersService {
    @Inject
    UsersRepository usersRepository;

    @Inject
    JwtUtilService jwtUtilService;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private MyUserDetailsService userDetailsService;

    public Optional<User> GetByEmail(String login) {
        return usersRepository.findByEmail(login);
    }

    public void createUser(User user) {
        if(noUsersInDatabase()){user.setRole(Role.ADMIN);}
         usersRepository.save(user);
    }

    public ResponseEntity<JwtResponse> getJwtResponseResponseEntity(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
        final String token = jwtUtilService.generateToken(userDetails);
        final Optional<User> user = GetByEmail(jwtAuthenticationRequest.getUsername());

        if(user.isPresent()) {
            final User foundUser = user.get();
            return ResponseEntity.ok(new JwtResponse(token, foundUser));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity authenticate(JwtAuthenticationRequest jwtAuthenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));
        return ResponseEntity.ok().build();
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public User findById(int id) {
        return usersRepository.findById(id).get();
    }

    public void update(User user) {
        Optional<User> existingUser = usersRepository.findById(user.getId());
        if(existingUser.isPresent()){
            User foundExistingUser = existingUser.get();

            foundExistingUser.setAddressCity(user.getAddressCity());
            foundExistingUser.setAddressNumber(user.getAddressNumber());
            foundExistingUser.setAddressStreetName(user.getAddressStreetName());
            foundExistingUser.setFirstName(user.getFirstName());
            foundExistingUser.setLastName(user.getLastName());
            foundExistingUser.setPassword(user.getPassword());


            usersRepository.save(foundExistingUser);
        }

    }

    public Page<User> findAll(Pageable pageable) {
        return usersRepository.findAllNonAdminUsers(pageable);
    }

    public Page<User> search(Pageable pageable, String keyword) {
        return usersRepository.search(pageable, keyword);
    }

    public void alterAccountStatusOfUserWithId(int userId) {
         usersRepository.alterAccountStatusOfUserWithId(userId);
    }

    public int blockedUsersNumber() {
        return usersRepository.countBlocked();
    }

    public long count() {
        return usersRepository.count();
    }

    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }

    public boolean noUsersInDatabase() {
        return count()==0;
    }
}

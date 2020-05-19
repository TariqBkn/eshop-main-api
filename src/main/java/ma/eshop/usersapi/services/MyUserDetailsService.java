package ma.eshop.usersapi.services;

import ma.eshop.usersapi.filters.CorsFilter;
import ma.eshop.usersapi.models.MyUserDetails;
import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetails.class);

    @Inject
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> user = usersRepository.findByEmail(email);
         if(user.isPresent()){
             User foundUser = user.get();
             return new MyUserDetails(foundUser);
         }else{
             LOGGER.debug("Username or password invalid.");
             throw new UsernameNotFoundException("can't find user with provided credentials");
         }
    }

}

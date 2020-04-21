package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.elasticsearch.common.inject.Inject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    @Inject
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);

         if(user.isPresent()){
             User foundUser = user.get();
             //TODO: Check the validity of this method
             return new MyUserDetails(foundUser);
         }else{
             throw new UsernameNotFoundException("Invalid email!");
         }
    }
}

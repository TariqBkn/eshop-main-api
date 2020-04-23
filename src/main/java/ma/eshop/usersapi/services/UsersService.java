package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UsersService {
    @Inject
    UsersRepository usersRepository;

    public Optional<User> GetByEmail(String login) {
        return usersRepository.findByEmail(login);
    }

    public void createUser(User user) {
        System.out.println("username"+user.getEmail());
         usersRepository.save(user);
    }
}

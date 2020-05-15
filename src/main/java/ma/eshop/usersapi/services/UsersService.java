package ma.eshop.usersapi.services;

import ma.eshop.usersapi.models.User;
import ma.eshop.usersapi.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            foundExistingUser.setAdressStreetName(user.getAddressStreetName());
            foundExistingUser.setFirstName(user.getFirstName());
            foundExistingUser.setLastName(user.getLastName());
            foundExistingUser.setPassword(user.getPassword());


            usersRepository.save(foundExistingUser);
        }

    }

    public Page<User> findAll(Pageable pageable) {
        return usersRepository.findAllNonAdminUsers(pageable);
    }
}

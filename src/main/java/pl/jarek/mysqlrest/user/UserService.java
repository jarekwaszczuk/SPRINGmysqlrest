package pl.jarek.mysqlrest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarek.mysqlrest.password.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final int MAX_PASSWORDS = 5;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordValidator passwordValidator;
    private final PasswordRepository passwordRepository;

    @Autowired //opcjonalny
    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordValidator passwordValidator, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordValidator = passwordValidator;
        this.passwordRepository = passwordRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        //TODO email unikalny
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException(userDTO.getEmail());
                });
        //TODO firstName, lastName powinny być podane
        //TODO password hasło wymagania
        if (!passwordValidator.valid(userDTO.getPassword())) {
            throw new InvalidPasswordException("Password is not valid");
        }

        User user = userConverter.toEntity(userDTO);
        user.setActivationKey(UUID.randomUUID().toString());
        return userConverter.toDTO(userRepository.save(user));
    }

    public UserDTO findById(Integer id) {
        return userConverter.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));
    }

    public List<UserDTO> findAll() {
        List<UserDTO> listaDTO = new ArrayList<>();
        Iterable<User> lista = userRepository.findAll();

        for (User user : lista) {
            listaDTO.add(userConverter.toDTO(user));
        }

        return listaDTO;
    }

    public void changePassword(Integer id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getPassword().equals(oldPassword)) throw new InvalidPasswordException("Old password is invalid");

        if (!passwordValidator.valid(newPassword)) throw new InvalidPasswordException("Password is to week");

        List<Password> history = passwordRepository.findByUserOrderByIdAsc(user);

        if (passwordInHistory(newPassword, history)) {
            throw new InvalidPasswordException("Password same as one from 5 last");
        }

        user.setPassword(newPassword);
        Password oldPass = new Password();

        if (history.size() < MAX_PASSWORDS) {
            oldPass.setUser(user);
            oldPass.setValue(oldPassword);
            history.add(oldPass);
        } else {
            int idPass = history.get(0).getId();
            history.remove(0);
            oldPass.setUser(user);
            oldPass.setValue(oldPassword);
            history.add(oldPass);
            passwordRepository.deleteById(idPass);
        }

        user.setOldPasswords(history);
        passwordRepository.save(oldPass);
        userRepository.save(user);
    }

    public void activate(Integer id, String activationKey) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (user.getActivationKey().equals(activationKey)) {
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new ActivationKeyNotValid(id);
        }
    }

    private boolean passwordInHistory(String newPassword, List<Password> history) {
        return history.stream()
                .anyMatch(historicalPassword -> historicalPassword.getValue().equals(newPassword));
    }
}

package pl.jarek.mysqlrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordValidator passwordValidator;

    @Autowired //opcjonalny
    public UserService(UserRepository userRepository, UserConverter userConverter, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordValidator = passwordValidator;
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
            throw new InvalidPasswordException();
        }

        User user = userConverter.toEntity(userDTO);
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

    public UserDTO resetPassword(Integer id, PasswordDTO passwordDTO) {
        User user = userRepository.findById(id).get();
        if (user.getPassword().equals(passwordDTO.getOldPassword())){
            user.setPassword(passwordDTO.getNewPassword());
            userRepository.save(user);
        } else {
            throw new OldPasswordIncorrect(id);
        }
        return userConverter.toDTO(user);
    }

    public UserDTO activate(Integer id, String activationKey) {
        User user = userRepository.findById(id).get();
        if(user.getActivationKey().equals(activationKey)){
            user.setActive(true);
            userRepository.save(user);
        }else{
            throw new ActivationKeyNotValid(id);
        }
        return userConverter.toDTO(user);
    }
}

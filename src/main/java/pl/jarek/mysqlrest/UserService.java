package pl.jarek.mysqlrest;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        return userConverter.toDTO(userRepository.save(user));
    }
}

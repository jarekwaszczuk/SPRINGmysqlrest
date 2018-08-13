package pl.jarek.mysqlrest;

import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
